package libetal.libraries.kuery.mariadb

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.extensions.FROM
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.core.statements.extensions.WHERE
import libetal.libraries.kuery.mariadb.database.Database
import libetal.libraries.kuery.mariadb.database.tables.Users
import kotlin.test.Test
import kotlin.test.assertEquals

class StatementTest {


    @Test
    fun testCreateTable() {
        assertEquals(
            """|CREATE TABLE `users` (
               |    `name` VARCHAR(55),
               |    `dob` DATE
               |);
            """.trimMargin(),
            createSimpleTableStatement.toString()
        )
    }

    @Test
    fun testEqualsOperator() {
        assertEquals("`name` == '$userName'", (Users.name equals userName).sql)
    }

    @Test
    fun testSimpleInsertStatementBuild() {
        assertEquals("INSERT INTO `users` (`name`) VALUES ('Breimer')", insertNewUser.toString())
    }

    @Test
    fun simpleSelectStatement() {
        assertEquals("SELECT * FROM `users` WHERE true", selectAllUsers.toString())
    }

    @Test
    fun simpleSelectSpecificColumnsStatement() {
        assertEquals("SELECT `name` FROM `users` WHERE true", selectSpecificUsersColumns.toString())
    }

    @Test
    fun simpleDeleteStatementBuild() { // TODO: Investigate in some crazy situation userName was in lowerCase from deleteNewUser.toString()
        assertEquals("DELETE FROM `users` WHERE `name` == '$userName'", deleteNewUser.toString())
    }

    @Test
    fun simpleUpdateStatementBuild() {

    }


    companion object {

        const val userName = "Breimer"

        val selectAllUsers by laziest {
            SELECT ALL Users WHERE true
        }

        val selectSpecificUsersColumns by laziest {
            SELECT(Users.name) FROM Users WHERE true
        }

        val insertNewUser by laziest {
            INSERT INTO Users VALUES {
                it.name set userName
            }
        }

        val deleteNewUser by laziest {
            DELETE * Users WHERE (Users.name equals userName)
        }

        val createSimpleTableStatement by laziest {
            with(Database) {
                CREATE TABLE Users
            }
        }

    }

}