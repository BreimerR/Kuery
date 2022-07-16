package libetal.libraries.kuery.database

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
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.database.extensions.listeners
import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector

class Connector : SQLiteOpenHelper, CoreConnector {

    override val dbName: String
        get() = this.databaseName

    @RequiresApi(Build.VERSION_CODES.P)
    constructor(
        context: Context,
        name: String? = null,
        version: Int = 1,
        openParams: OpenParams,
    ) : super(context, name, version, openParams)

    /**
     * @param name String? null if in memory database
     **/
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
    )

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
        writableDatabase.execSQL(statement.toString())
    }

    override fun <T> execute(statement: Statement) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {

        private const val TAG = "Connector"

        private var instance: Connector? = null

        val INSTANCE: Connector by laziest{
            instance ?: throw RuntimeException("Call init(context) first")
        }

        fun init(context: Context): Connector = instance ?: Connector(context).also {
            instance = it
        }

        operator fun invoke(): Connector = INSTANCE

    }

}