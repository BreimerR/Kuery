package libetal.libraries.kuery.mariadb

import libetal.libraries.kuery.mariadb.StatementTest.Companion.insertNewUser
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectSpecificUsersColumns
import libetal.libraries.kuery.mariadb.database.Database.query
import libetal.libraries.kuery.mariadb.database.tables.User
import libetal.libraries.kuery.mariadb.database.tables.Users
import kotlin.test.*

class CrudTest : TestCase {

    @BeforeTest
    fun simpleCreateTable() = StatementTest.createSimpleTableStatement query {
        assertNull(error, "Failed to create database")
    }

    @AfterTest
    fun simpleDropTable() = StatementTest.dropTableIfExistsStatement query {
        assertNull(error, "Failed to drop database with exception")
    }

    @Test
    fun simpleSelectSpecificColumns() {
        selectSpecificUsersColumns query {

        }
    }

    @Test
    fun simpleInsert() = insertNewUser query {
        selectAllUsers query {
            val user = User(
                name = get(Users to Users.name),
                dateOfBirth = get(Users to Users.dob),
            )

            assertEquals(StatementTest.userName, user.name)
            assertEquals(StatementTest.date, user.dateOfBirth)

        }
    }

}