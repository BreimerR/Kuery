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

class Connector(override val dbName: String, val version: Int/*TODO: Store value in the database*/) : CoreConnector {

    @Suppress("MemberVisibilityCanBePrivate") // public for current testing should be private
    val connection by laziest {
        // TODO: I think databse is created if it doesn't exist
        connect(dbName, staticCFunction { code, description ->
            TAG info (description?.toKString() ?: "Code: $code Undefined Exception: ")
        })
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

    suspend fun executeSQL(statement: Statement<*>) {
        connection.query(statement.toString())
    }

    override fun <R : Result> execute(statement: Statement<R>) {
        connection.query(statement.toString())
    }

    override fun query(sqlStatement: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(statement: Create<*, *>): Flow<CreateResult> {
        TODO("Not yet implemented")
    }

    override fun query(statement: Select): Flow<SelectResult> {
        TODO("Not yet implemented")
    }

    override fun query(statement: Delete): Flow<DeleteResult> {
        TODO("Not yet implemented")
    }

    override fun query(statement: Insert): Flow<InsertResult> {
        TODO("Not yet implemented")
    }

    override fun query(statement: Drop): Flow<DropResult> {
        TODO("Not yet implemented")
    }

    override fun query(statement: Update): Flow<UpdateResult> {
        TODO("Not yet implemented")
    }


}