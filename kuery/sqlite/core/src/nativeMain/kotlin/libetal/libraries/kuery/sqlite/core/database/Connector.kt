package libetal.libraries.kuery.sqlite.core.database

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.coroutines.flow.Flow
import libetal.interop.sqlite3.connect
import libetal.interop.sqlite3.sqlite3_close
import libetal.kotlin.debug.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Connector
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.sqlite.core.CoreKuery
import libetal.libraries.kuery.sqlite.database.query

actual class Connector(actual override val database: String, val version: Int/*TODO: Store value in the database*/) : Connector {

    @Suppress("MemberVisibilityCanBePrivate") // public for current testing should be private
    val connection by laziest {
        // TODO: I think databse is created if it doesn't exist
        connect(database, staticCFunction { code, description ->
            TAG info (description?.toKString() ?: "Code: $code Undefined Exception: ")
        })
    }


    fun close(): Unit = memScoped {
        val connection = connection.getPointer(this).pointed
        sqlite3_close(
            connection.db
        )
    }

    suspend fun executeSQL(statement: Statement<*>) {
        connection.query(statement.toString())
    }

    /*actual fun <R : Result> execute(statement: Statement<R>) {
        connection.query(statement.toString())
    }*/

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

    actual companion object {

        const val TAG = "Connector"

        val INSTANCE: Connector
            get() = TODO("Not yet implemented")

        actual operator fun invoke(): libetal.libraries.kuery.core.Connector {
            TODO("Not yet implemented")
        }

    }

    actual operator fun CoreKuery.invoke() {
    }

}