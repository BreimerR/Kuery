package libetal.libraries.kuery.mariadb

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.results.CreateResult
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.database.Database
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ConnectorTest : TestCase {

    @Test
    fun connection() {

    }

    fun simpleCreateTable() = StatementTest.createSimpleTableStatement query { result ->
        assertTrue(result is CreateResult)
        assertNull(result.error, "Failed to create database")
    }

    @Test
    fun executeSimpleSelect() = selectAllUsers query {

    }

    val results by laziest {
        mutableMapOf<String, String>()
    }


    companion object {
        val connector by laziest {
            Connector(
                Database.database,
                Database.user,
                Database.password,
                Database.host,
                Database.port
            )
        }
    }


}