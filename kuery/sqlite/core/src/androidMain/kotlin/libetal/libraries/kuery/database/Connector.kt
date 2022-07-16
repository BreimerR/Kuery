package libetal.libraries.kuery.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteDatabase.OpenParams
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.sqlite.core.database.extensions.listeners
import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector


class Connector : SQLiteOpenHelper, CoreConnector {

    private var mName = ""

    override val dbName: String
        get() = mName

    @RequiresApi(Build.VERSION_CODES.P)
    constructor(
        context: Context,
        name: String? = null,
        version: Int = 1,
        openParams: OpenParams,
    ) : super(context, name, version, openParams) {
        mName = name ?: "local"
    }

    /**
     * @param name String? null if in memory database
     **/
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(
        context: Context,
        name: String? = null,
        version: Int = 1,
        cursorFactory: CursorFactory? = null,
        errorHandler: DatabaseErrorHandler? = null
    ) : super(
        context,
        name,
        cursorFactory,
        version,
        errorHandler
    ) {
        mName = name ?: "LOCAL_DATABASE"
    }

    /**
     * Not called until a query actually
     * tries to be executed
     **/
    override fun onCreate(db: SQLiteDatabase?) {
        listeners.forEach {
            it.onCreate(this)
        }
    }


    override suspend fun executeSQL(statement: Statement<*>) = withContext(Dispatchers.IO) {
        when (statement) {
            is Select -> {

            }
            is Insert -> {

            }
            is Update -> {

            }
            else -> writableDatabase.execSQL(statement.sql)
        }
    }

    override fun <T, E : Entity<T>, S : Statement<T, E>> execute(statement: S) {
        when (statement) {
            is Select<*, *> -> {
                val whereArgs: Array<String>
                val where = buildString {
                    val whereScope = this

                    whereArgs = buildList {
                        val whereArgsScope = this

                        var i = 0
                        for ((column, value) in statement.wheres) {
                            val coma = if (i == 0) "" else ","
                            whereScope.append("$coma`${column.name}` = ?")
                            whereArgsScope.add(value.toString())
                            i++
                        }

                    }.toTypedArray()
                }

                val cursor = writableDatabase.query(
                    statement.entity.name,
                    statement.columns.map { it.name }.toTypedArray(),
                    where,
                    whereArgs,
                    statement.groupBy?.name,
                    null, // TODO: String Argument relevance not clear yet
                    statement.orderBy?.name,
                    statement.limit?.toString()
                )

                cursor.use {

                }
            }
            is Insert<*, *> -> {
                val values = ContentValues().apply {
                }

                /**
                 * Null column hack not considered here.
                 * Not sure if the feature is well-used / how to
                 * solve for its use case in this project yet.
                 **/
                writableDatabase.insert(statement.entity.name, null, values)
            }
            is Update<*, *> -> {

            }

            is Delete<*, *> -> {
                val whereClause = ""

                val whereArgs = buildList<String> {

                }.toTypedArray()

                writableDatabase.delete(
                    statement.entity.name,
                    whereClause,
                    whereArgs
                )

            }
            else -> writableDatabase.execSQL(statement.sql)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {

        private const val TAG = "Connector"

        private var instance: Connector? = null

        val INSTANCE: Connector
            get() = instance ?: throw RuntimeException("Call init(context) first")

        @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        fun init(context: Context): Connector = instance ?: Connector(context).also {
            instance = it
        }

        operator fun invoke(): Connector = INSTANCE

    }

}