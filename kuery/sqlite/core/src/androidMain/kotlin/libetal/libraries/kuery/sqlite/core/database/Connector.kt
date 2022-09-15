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
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.database.extensions.listeners

actual class Connector : SQLiteOpenHelper, KSQLiteConnector {

    private var mName = ""

    override val name: String
        get() = mName

    var onCreateDb: SQLiteDatabase? = null

    val connection: SQLiteDatabase
        get() = if (onCreateDb == null)
            writableDatabase
        else
            onCreateDb!!

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
        } catch (e: RuntimeException) {
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

        val cursor = try {

            val selectionColumns = columns.map { it.name }.toTypedArray()
            val selectionArgs = statement.columnValues.map { it.toString() }.toTypedArray()

            TAG info "BoundWhere = $boundWhere"
            TAG info "Entity: ${statement.entity}"
            TAG info "Group: ${selectionColumns.joinToString(", ")}"
            TAG info "Columns: ${selectionColumns.joinToString(", ")}"
            TAG info "SelectionArgs: ${selectionArgs.joinToString(", ")}"

            with(readableDatabase) {
                rawQuery(
                    statement.boundSql,
                    if (selectionArgs.isEmpty()) null else selectionArgs
                ) ?: throw RuntimeException("Java based exception. Not sure why it's null yet.")
            }

        } catch (e: Exception) {
            emit(
                SelectResult(
                    emptyList(),
                    e
                )
            )
            return@flow
        }

        with(cursor) {

            while (moveToNext()) {

                val row = mutableListOf<Any?>()

                var error: Exception? = null

                try {

                    var i = 0

                    while (i < columns.size) {
                        columns[i].apply {
                            val value = getString(i) ?: null
                            row += parse(value)
                        }
                        i++
                    }
                } catch (e: Exception) {
                    error = e
                }

                TAG info "Collected $row."

                emit(
                    SelectResult(
                        row,
                        error,
                        *columns
                    )
                )
            }

            close()
        }

    }

    override fun query(statement: Delete): Flow<DeleteResult> = flow {
        val whereClause = ""

        val whereArgs = buildList<String> {

        }.toTypedArray()

        val error = try {
            connection.delete(
                statement.entity.name,
                whereClause,
                whereArgs
            )
            null
        } catch (e: Exception) {
            e
        }

        emit(
            DeleteResult(
                statement.entity.name,
                error
            )
        )
    }

    override fun query(statement: Insert): Flow<InsertResult> = flow {

        TAG info "Executing  $statement"

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

        val error = try {
            connection.insert(statement.entity.name, null, values)
            null
        } catch (e: Exception) {
            e
        }

        emit(
            InsertResult(
                statement.entity.name,
                error
            )
        )

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

    actual operator fun Kuery.invoke() {
        init()
    }

}