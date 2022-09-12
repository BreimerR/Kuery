package libetal.libraries.kuery.sqlite.core.database

import kotlinx.cinterop.*
import kotlinx.cinterop.memScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import libetal.interop.sqlite3.*
import libetal.kotlin.io.File
import libetal.kotlin.io.exists
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Connector
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.entities.extensions.type
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.Select
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.exceptions.KSQLiteQueryException

// import libetal.libraries.kuery.sqlite.exceptions.KSQLiteQueryException

actual class Connector
private constructor(val path: String?, override val name: String?, version: Int) : KSQLiteConnector {

    var isNewDatabase = false

    @Suppress("MemberVisibilityCanBePrivate") // public for current testing should be private
    var connection by laziest({ createdConnection ->
        if (isNewDatabase) {

        }
    }) {
        val database = path?.let { name?.let { "${path.trimEnd('/')}$name" } }
            ?: throw RuntimeException("In Memory database not supported for native. Argument name & path should not be null")

        memScoped {

            val dbPtr = alloc<CPointerVar<sqlite3>>()
            isNewDatabase = !File(database).exists

            when (val result = sqlite3_open(database, dbPtr.ptr)) {
                0 -> dbPtr.value ?: throw RuntimeException("Failed to connect to database")
                else -> throw KSQLiteQueryException(result)
            }

        }
    }

    private constructor(name: String?, version: Int) : this(
        name?.let { KSQLiteConnector.resolveName(name).first },
        name?.let { KSQLiteConnector.resolveName(name).second },
        version
    )

    suspend fun <T> execute(sql: String) = flow<T> {

    }

    /*actual fun <R : Result> execute(statement: Statement<R>) {
        connection.query(statement.toString())
    }*/

    override fun query(sqlStatement: String): Boolean {
        var error: String? = null
        /*connection.query(sqlStatement) { code, description ->
            error = description
        }*/
        return error == null;
    }

    override fun query(statement: Create<*, *>) = flow<CreateResult> {
        var exception: RuntimeException? = null

        /*connection.query(statement.sql) { code, desscription ->
            exception = KSQLiteQueryException(code)
        }*/

        emit(
            CreateResult(
                statement.entity.name,
                statement.entity.type,
                exception
            )
        )

    }

    override fun query(statement: Select) = flow<SelectResult> {

        val emissions = mutableListOf<SelectResult>()
        val callbackPointer = StableRef.create { columsCount: Int, rowValues: CStringArray, columnNames: CStringArray? ->
            /** TODO
             * I want to pass rowValues as it is to avoid the
             * conversion loop
             **/
            /* SelectResult(
                 ,
                 null,
                 *statement.columns,
             )*/
        }

        val results = memScoped {

            sqlite3_exec(connection, statement.sql, staticCFunction { callback, columnsCount, rowValues, columnsNames ->
                val callbackFunction =
                    callback?.asStableRef<(Int, CStringArray?, CStringArray?) -> Int>()
                        ?.get() ?: throw RuntimeException("Failed to assign callback function")
                callbackFunction(
                    columnsCount,
                    columnsNames,
                    rowValues
                )
            }, callbackPointer.asCPointer(), null)

            arrayOf<String>()
        }

        for (emission in emissions) {
            emit(emission)
        }

        emissions.clear()

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


    fun close(): Unit {
        sqlite3_close(connection)
    }


    actual companion object {

        const val TAG = "Connector"

        operator fun invoke(path: String?, name: String?, version: Int = 1) = INSTANCE ?: Connector(path, name, version).also {
            INSTANCE = it
        }

        actual operator fun invoke(): Connector = INSTANCE ?: throw RuntimeException("Please call invoke with dbName first")

    }

    actual operator fun Kuery.invoke() {
    }

}