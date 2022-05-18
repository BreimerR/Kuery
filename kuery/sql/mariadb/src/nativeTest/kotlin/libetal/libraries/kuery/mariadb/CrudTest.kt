package libetal.libraries.kuery.mariadb

import libetal.libraries.kuery.mariadb.ConnectorTest.Companion.connector
import libetal.libraries.kuery.mariadb.StatementTest.Companion.createSimpleTableStatement
import libetal.libraries.kuery.mariadb.StatementTest.Companion.insertNewUser
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectSpecificUsersColumns
import kotlin.test.Test

class CrudTest : TestCase {

    @Test
    fun simpleCreateTable() = createSimpleTableStatement query { (message, description) ->
        println("$message $description")
    }

    @Test
    fun simpleSelect() {
        (connector query selectAllUsers){ (column, value): Pair<String, String> ->
            println("$column $value")
        }
    }

    @Test
    fun simpleSelectSpecificColumns() = selectSpecificUsersColumns query { (column, value) ->
        println("$column $value")
    }

    @Test
    fun simpleInsert() = insertNewUser query { (column, value) ->
        println("$column $value")
    }

    @Test
    fun simpleDelete() {

    }

}