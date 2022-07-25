package libetal.libraries.kuery.sqlite.core.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.entities.extensions.type
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.sqlite.core.database.extensions.listeners

actual class Connector : SQLiteOpenHelper, libetal.libraries.kuery.core.Connector {

    private var mName = ""

    actual override val database: String
        get() = mName

    var onCreateDb: SQLiteDatabase? = null

    val connection: SQLiteDatabase
        get() = onCreateDb ?: writableDatabase // TODO there is ambiguity here when the database getsCalled recusively

    @RequiresApi(Build.VERSION_CODES.P)
    constructor(
        context: Context,
        name: String? = null,
        version: Int = 1,
        openParams: SQLiteDatabase.OpenParams,
    ) : super(context, name, version, openParams) {
        mName = name ?: "local"
        instance = this
        TAG info "Connected to database"
    }

    /**
     * @param name String? null if in memory database
     **/
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(
        context: Context,
        name: String? = null,
        version: Int = 1,
        cursorFactory: SQLiteDatabase.CursorFactory? = null,
        errorHandler: DatabaseErrorHandler? = null
    ) : super(
        context,
        name,
        cursorFactory,
        version,
        errorHandler
    ) {
        mName = name ?: "LOCAL_DATABASE"
        instance = this
        TAG info "Connected to database"

    }

    /**
     * Not called until a query actually
     * tries to be executed
     **/
    override fun onCreate(db: SQLiteDatabase?) {
        onCreateDb = db
        listeners.forEach {
            it.onCreate(this)
        }
        onCreateDb = null
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    /*actual fun <R : Result> execute(statement: Statement<R>) {
       connection.execSQL(statement.sql)
   }*/

    override fun query(sqlStatement: String): Boolean = try {
        connection.execSQL(sqlStatement)
        true
    } catch (e: Exception) {
        false
    }

    override fun query(statement: Create<*, *>): Flow<CreateResult> = flow {
        val error = try {
            connection.execSQL("$statement")
            null
        } catch (e: Exception) {
            e
        }
        emit(
            CreateResult(
                "${statement.entity}",
                statement.entity.type,
                error
            )
        )
    }

    override fun query(statement: Select): Flow<SelectResult> = flow {
        val columns = statement.columns
        val boundWhere = statement.boundWhere

        val cursor = connection.query(
            statement.entity.name,
            columns.map { it.name }.toTypedArray(),
            boundWhere,
            statement.columnValues.map { it.toString() }.toTypedArray(),
            statement.groupBy?.name,
            null, // TODO: String Argument relevance not clear yet
            statement.orderBy?.name,
            statement.limit?.toString()
        ) ?: throw RuntimeException("Java based exception. Not sure why it's null yet.")

        with(cursor) {


            while (moveToNext()) {
                val row = mutableListOf<Any?>()

                TAG info "Row results are: $row"

                var i = 0

                while (i < columns.size) {
                    val value = getString(i) ?: null
                    row += columns[i].parse(value)
                    i++
                }

                emit(
                    SelectResult(
                        row,
                        null,
                        *columns
                    )
                )
            }
        }
        cursor.close()

    }

    override fun query(statement: Delete): Flow<DeleteResult> = flow {
        val whereClause = ""

        val whereArgs = buildList<String> {

        }.toTypedArray()

        connection.delete(
            statement.entity.name,
            whereClause,
            whereArgs
        )

    }

    override fun query(statement: Insert): Flow<InsertResult> = flow {
        val values = ContentValues().apply {
            val columns = statement.columns
            val rows = statement.values

            for (row in rows) {
                var i = 0
                while (i < row.size) {
                    val column = columns[i]
                    val value = row[i]

                    val name = column.name

                    when (value) {
                        is Long -> put(name, value)
                        is Byte -> put(name, value)
                        is Float -> put(name, value)
                        is Short -> put(name, value)
                        is String -> put(name, value)
                        is Double -> put(name, value)
                        is Boolean -> put(name, value)
                        is ByteArray -> put(name, value)
                        is Int -> put(column.name, value)
                        else -> throw RuntimeException("Unsupported data type ${value?.let { it::class.simpleName }}")
                    }

                    i++
                }
            }

        }

        connection.insert(statement.entity.name, null, values)

    }

    override fun query(statement: Drop): Flow<DropResult> = flow {
        val result = query("$statement")
    }

    override fun query(statement: Update): Flow<UpdateResult> {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        throw RuntimeException("SqliteOnUpgrade: Called")
    }

    actual companion object {

        private const val TAG = "Connector"

        private var instance: Connector? = null

        actual operator fun invoke(): libetal.libraries.kuery.core.Connector =
            instance ?: throw RuntimeException("Please refer to the documentation on how to initialize the database")

    }

}