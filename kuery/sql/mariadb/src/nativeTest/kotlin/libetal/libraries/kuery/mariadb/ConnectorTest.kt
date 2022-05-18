package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.core.statements.extensions.WHERE
import libetal.libraries.kuery.mariadb.StatementTest.Companion.selectAllUsers
import libetal.libraries.kuery.mariadb.database.Database
import libetal.libraries.kuery.mariadb.database.tables.User
import libetal.libraries.kuery.mariadb.database.tables.Users
import platform.posix.sleep
import kotlin.test.Test
import kotlin.test.assertEquals

class ConnectorTest : TestCase {

    @Test
    fun connection() {

    }

    @Test
    fun executeSimpleSelect() = selectAllUsers query { (column, value) ->
        println("$column $value")
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