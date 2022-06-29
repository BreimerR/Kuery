package libetal.libraries.kuery.sqlite.database

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.core.statements.DELETE
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.core.statements.extensions.FROM
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.core.statements.extensions.WHERE
import libetal.libraries.kuery.sqlite.database.Database.TABLE
import libetal.libraries.kuery.sqlite.database.tables.Users
import kotlin.test.Test
import kotlin.test.assertEquals

class StatementTest {

    @Test
    fun createTableStatementTest() {
        assertEquals(
            """CREATE TABLE IF NOT EXISTS `users` (
            |    user NUMERIC NOT NULL,
            |    name TEXT NOT NULL,
            |    age NUMERIC NOT NULL
            |);
        """.trimMargin(), createTableStatement.toString()
        )
    }

    @Test
    fun selectTest() {
        assertEquals("SELECT `user`, `name`, `age` FROM `users` WHERE `age` == 1", selectStatement.toString())
    }


    @Test
    fun insertTest() {
        assertEquals("INSERT INTO `users` (`name`) VALUES (Breimer)", insertStatement.toString())
    }

    @Test
    fun deleteTest(){
        assertEquals("DELETE FROM `users`",deleteStatement.toString())
    }

    companion object {

        val createTableStatement by laziest {
            CREATE TABLE Users
        }

        val selectStatement by laziest {
            SELECT * Users WHERE (Users.age equals 1)
        }

        val insertStatement by laziest {
            INSERT INTO Users VALUES {
                Users.name set "Breimer"
            }
        }

        val deleteStatement by laziest {
            DELETE FROM Users
        }

    }

}