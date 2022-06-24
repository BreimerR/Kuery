package libetal.libraries.kuery.mariadb

import libetal.libraries.kuery.mariadb.StatementTest.Companion.createSimpleTableStatement
import libetal.libraries.kuery.mariadb.StatementTest.Companion.dropTableIfExistsStatement
import libetal.libraries.kuery.mariadb.StatementTest.Companion.insertNewUser
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectSpecificUsersColumns
import libetal.libraries.kuery.mariadb.statements.CreateResult
import libetal.libraries.kuery.mariadb.statements.DeleteResult
import libetal.libraries.kuery.mariadb.statements.InsertResult
import libetal.libraries.kuery.mariadb.statements.SelectResult
import kotlin.test.*

class CrudTest : TestCase {

    @BeforeTest
    fun simpleCreateTable() = StatementTest.createSimpleTableStatement query { result ->
        assertTrue(result is CreateResult)
        assertNull(result.error, "Failed to create database")
    }

    @AfterTest
    fun simpleDropTable() = StatementTest.dropTableIfExistsStatement query { result ->
        assertTrue(result is DeleteResult)
        assertNull(result.error, "Failed to drop database with exception")
    }

    fun simpleSelectSpecificColumns() = selectSpecificUsersColumns query {
        //  println("$column $value")
    }

    @Test
    fun simpleInsert() = insertNewUser query { insertResult ->
        assertTrue(insertResult is InsertResult)
        selectAllUsers query { selectResult ->
            assertTrue(selectResult is SelectResult)
            println("${selectResult.columnName} ${selectResult.value}")
        }
    }


    fun simpleDelete() {

    }

}