package libetal.libraries.kuery.mariadb

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.results.CreateResult
import libetal.libraries.kuery.core.statements.results.SelectResult
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.database.Database
import libetal.libraries.kuery.mariadb.database.Database.query
import libetal.libraries.kuery.mariadb.database.tables.User
import libetal.libraries.kuery.mariadb.database.tables.Users
import kotlin.test.*

class ConnectorTest : TestCase {

    @BeforeTest
    fun simpleCreateTable() = StatementTest.createSimpleTableStatement query {
        assertNull(error, "Failed to create database")
    }

    @AfterTest
    fun simpleDropTable() = StatementTest.dropTableIfExistsStatement query {

    }

    @Test
    fun connection() {

    }

    @Test
    fun executeSimpleSelect() = selectAllUsers query {
        val user = User(
            name = get(Users to Users.name),
            dateOfBirth = get(Users to Users.dob)
        )

        assertEquals(StatementTest.userName, user.name)
        assertEquals(StatementTest.date, user.dateOfBirth)

    }


    val results by laziest {
        mutableMapOf<String, String>()
    }


    companion object {

    }


}