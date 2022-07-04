package libetal.libraries.kuery.mariadb

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.Existence.EXISTS
import libetal.libraries.kuery.core.statements.extensions.*
import libetal.libraries.kuery.mariadb.database.Database
import libetal.libraries.kuery.mariadb.database.Database.TABLE
import libetal.libraries.kuery.mariadb.database.tables.Users
import kotlin.test.Test
import kotlin.test.assertEquals

class StatementTest {

    @Test
    fun testCreateTable() {
        assertEquals(
            """|CREATE TABLE IF NOT EXISTS `users` (
               |    `name` VARCHAR(55) NOT NULL,
               |    `dob` DATE NOT NULL
               |);
            """.trimMargin(),
            createSimpleTableStatement.toString()
        )
    }

    @Test
    fun testDropSimpleTable() {
        assertEquals(
            """DROP TABLE `users`""",
            dropSimpleTable.toString()
        )
    }

    @Test
    fun testDropTableIfExists() {
        assertEquals(
            """DROP TABLE IF EXISTS `users`""",
            dropTableIfExistsStatement.toString()
        )
    }

    @Test
    fun testEqualsOperator() {
        assertEquals("`name` = '$userName'", (Users.name equals userName).sql)
    }

    @Test
    fun testSimpleInsertStatementBuild() {
        assertEquals("INSERT INTO `users` (`name`, `dob`) VALUES ('Breimer', '$date')", insertNewUser.toString())
    }

    @Test
    fun simpleSelectStatement() {
        assertEquals("SELECT `name`, `dob` FROM `users` WHERE true", selectAllUsers.toString())
    }

    @Test
    fun simpleSelectSpecificColumnsStatement() {
        assertEquals("SELECT `name` FROM `users` WHERE true", selectSpecificUsersColumns.toString())
    }

    @Test
    fun simpleDeleteStatementBuild() { // TODO: Investigate in some crazy situation userName was in lowerCase from deleteNewUser.toString() Trust me it happened once without lowering anywhere. I'm not crazy.
        assertEquals("DELETE FROM `users` WHERE `name` = '$userName'", deleteNewUser.toString())
    }

    @Test
    fun simpleUpdateStatementBuild() {
        assertEquals("UPDATE `users` SET `name` = 'Breimer' WHERE `name` = 'Lazie'", updateSimpleTableStatement.toString())
    }


    companion object {

        const val userName = "Breimer"

        val selectAllUsers by laziest {
            SELECT * Users WHERE true
        }

        val selectSpecificUsersColumns by laziest {
            SELECT(Users.name) FROM Users WHERE true
        }

        val date by laziest {
            Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        }

        val insertNewUser by laziest {
            (INSERT INTO Users VALUES {
                it.name set userName
                it.dob set date
            }).also {
                println("SQL: $it")
            }
        }

        val deleteNewUser by laziest {
            DELETE ALL Users WHERE (Users.name equals userName)
        }

        val createSimpleTableStatement by laziest {
            CREATE TABLE Users  // TODO IF NOT EXISTS
        }

        val updateSimpleTableStatement by laziest {
            UPDATE TABLE Users SET {
                Users.name to "Breimer"
            } WHERE (Users.name equals "Lazie")
        }

        val dropTableIfExistsStatement by laziest {
            dropSimpleTable IF EXISTS
        }

        val dropSimpleTable by laziest {
            DROP TABLE Users
        }

    }

}