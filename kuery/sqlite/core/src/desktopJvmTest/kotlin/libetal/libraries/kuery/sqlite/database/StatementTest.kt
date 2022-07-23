package libetal.libraries.kuery.sqlite.database

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.columns.extensions.greaterThan
import libetal.libraries.kuery.core.expressions.extensions.AND
import libetal.libraries.kuery.core.expressions.extensions.OR
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.DELETE.FROM
import libetal.libraries.kuery.core.statements.extensions.FROM
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.sqlite.database.tables.Users
import kotlin.test.Test
import kotlin.test.assertEquals

class StatementTest {

    @Test
    fun createTableStatementTest() {
        assertEquals(
            """CREATE TABLE IF NOT EXISTS `users` (
            |    `id` NUMERIC PRIMARY KEY AUTOINCREMENT,
            |    `name` TEXT NOT NULL,
            |    `age` INTEGER NOT NULL
            |);
            """.trimMargin(), createTableStatement.toString()
        )
    }

    @Test
    fun selectTest() {
        assertEquals("SELECT `id`, `name`, `age` FROM `users` WHERE true", selectStatement.toString())
    }

    @Test
    fun simpleBoundSelect() {
        assertEquals("SELECT `id`, `name`, `age` FROM `users` WHERE `age` = ?", selectStatement.boundSql)
    }

    @Test
    fun simpleJoinedExpressionTest() {
        val statement = SELECT ALL Users WHERE (Users.name equals "Breimer" AND {
            Users.age equivalent 1
        })

        assertEquals("SELECT `id`, `name`, `age` FROM `users` WHERE `name` = 'Breimer' AND `age` = 1", statement.toString())

    }

    @Test
    fun averageJoinedExpressionTest() {
        val statement =
            SELECT ALL Users WHERE ((Users.name equals "Lazie") AND (Users.age equals 1 OR (Users.name equals "Breimer")))

        assertEquals(
            "SELECT `id`, `name`, `age` FROM `users` WHERE `name` = 'Lazie' AND (`age` = 1 OR `name` = 'Breimer')",
            statement.sql
        )
    }

    @Test
    fun averageJoinedExpressionAlternativeTest() {
        with(
            SELECT ALL Users WHERE ((Users.name equals "Lazie" AND (Users.age equals 1)) OR (Users.name equals "Breimer"))
        ) {
            assertEquals(
                "SELECT `id`, `name`, `age` FROM `users` WHERE (`name` = 'Lazie' AND `age` = 1) OR `name` = 'Breimer'",
                sql
            )
        }
    }


    @Test
    fun insertTest() {
        assertEquals("INSERT INTO `users`(`name`, `age`) VALUES ('Breimer', 12), ('Breimer', 12)", insertStatement.toString())
    }

    @Test
    fun nestedSelectStatementTest() {
        assertEquals(
            "SELECT `id`, `name`, `age` FROM `users` WHERE `age` = (SELECT AVG(`age`) FROM `users` WHERE true)",
            nestedSelectStatement.sql
        )
    }

    @Test
    fun boundInsertTest() {
        assertEquals("INSERT INTO `users`(`name`, `age`) VALUES (?, ?), (?, ?)", insertStatement.boundSql)
    }

    @Test
    fun deleteTest() {
        assertEquals("DELETE FROM `users` WHERE `name` = 'Breimer'", deleteUserStatement.toString())
    }

    @Test
    fun boundDeleteTest() {
        assertEquals("DELETE FROM `users` WHERE `name` = ?", deleteUserStatement.boundSql)
    }

    companion object {

        val createTableStatement by laziest {
            CREATE TABLE Users
        }

        val selectStatement by laziest {
            SELECT * Users WHERE true
        }

        val nestedSelectStatement by laziest {
            SELECT * Users WHERE (Users.age greaterThan (SELECT AVG Users.age FROM Users WHERE true))
        }

        /**
         * INSERT INTO Users(vararg column) VALUES {
         *     add(value1, ... column.size)
         *     add(value2, ... column.size)
         * }
         **/
        val insertStatement by laziest {
            INSERT INTO Users(Users.name, Users.age) VALUES {
                row("Breimer", 12)
                row("Breimer", 12)
            }
        }

        val deleteUserStatement by laziest {
            DELETE FROM Users WHERE (Users.name equals "Breimer")
        }

        val dropTableStatement by laziest {
            DROP TABLE Users IF Existence.EXISTS
        }

        private const val TAG = "StatementTest"

    }

}