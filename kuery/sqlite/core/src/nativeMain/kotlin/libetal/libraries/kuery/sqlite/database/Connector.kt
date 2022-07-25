package libetal.libraries.kuery.sqlite.database

import kotlinx.cinterop.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import libetal.interop.sqlite3.Connection as SqliteConn
import libetal.interop.sqlite3.connect
import libetal.interop.sqlite3.sqlite3_close
import libetal.kotlin.debug.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.core.statements.results.Result
import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector

fun CValue<SqliteConn>.query(sql: String) {
    TODO("Integration not complete on building sqlite for C platforms")
}