package libetal.libraries.kuery.sqlite.database

import kotlinx.cinterop.*
import libetal.interop.sqlite3.Connection as SqliteConn
import libetal.interop.sqlite3.connect
import libetal.interop.sqlite3.sqlite3_close
import libetal.kotlin.debug.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector

fun CValue<SqliteConn>.query(sql: String) {

}

class Connector(override val dbName: String, val version: Int/*TODO: Store value in the database*/) : CoreConnector {

    @Suppress("MemberVisibilityCanBePrivate") // public for current testing should be private
    val connection by laziest {
        // TODO: I think databse is created if it doesn't exist
        connect(dbName, staticCFunction { code, description ->
            TAG info (description?.toKString() ?: "Code: $code Undefined Exception: ")
        })
    }

    override suspend fun executeSQL(statement: Statement<*>) {
        connection.query(statement.toString())
    }

    override fun <T> execute(statement: Statement) {
        connection.query(statement.toString())
    }

    fun close(): Unit = memScoped {
        val connection = connection.getPointer(this).pointed
        sqlite3_close(
            connection.db
        )
    }

    companion object {

        const val TAG = "Connector"

        val INSTANCE: Connector
            get() = TODO("Not yet implemented")

        operator fun invoke(): Connector {
            TODO("Not yet implemented")
        }

    }


}