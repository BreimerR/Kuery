package libetal.libraries.kuery.database

import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.core.statements.results.Result
import java.sql.DriverManager

class Connector
private constructor(override val dbName: String, val version: Int) : CoreConnector {

    val connection by laziest {
        try {
            Class.forName("org.sqlite.JDBC")
            val fileName = when (dbName.substringAfterLast(".")) {
                "db", "sqlite" -> dbName
                else -> "$dbName.sqlite"
            }
            DriverManager.getConnection("jdbc:sqlite:$fileName") ?: null
        } catch (e: Exception) {
            null
        } ?: throw RuntimeException("Failed to open databas")
    }

    companion object {

        private var instance: Connector? = null

        operator fun invoke(dbName: String, version: Int): Connector = instance ?: Connector(dbName, version).also {
            instance = it
        }

    }

    override fun <R : Result> execute(statement: Statement<R>) {
        TODO("Not yet implemented")
    }

    override fun query(sqlStatement: String): Boolean {
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