package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.runBlocking
import libetal.libraries.kuery.mariadb.StatementTest.Companion.insertNewUser
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectSpecificUsersColumns
import libetal.libraries.kuery.mariadb.database.Database.query
import libetal.libraries.kuery.mariadb.database.tables.User
import libetal.libraries.kuery.mariadb.database.tables.Users
import kotlin.test.*

class CrudTest : TestCase {

    @BeforeTest
    fun simpleCreateTable() = runBlocking {
        StatementTest.createSimpleTableStatement query {
            assertNull(error, "Failed to create database")
        }
    }

    @AfterTest
    fun simpleDropTable() = runBlocking {
        StatementTest.dropTableIfExistsStatement query {
            assertNull(error, "Failed to drop database with exception")
        }
    }

    @Test
    fun simpleSelectSpecificColumns() = runBlocking {
        selectSpecificUsersColumns query {

        }
    }

    @Test
    fun simpleInsert() = runBlocking {
        insertNewUser query {

        }

        selectAllUsers query {
            val user = User(
                name = get(0),
                dateOfBirth = get(1),
            )

            assertEquals(StatementTest.userName, user.name)
            assertEquals(StatementTest.date, user.dateOfBirth)

        }

    }

}