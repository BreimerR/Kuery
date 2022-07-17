package libetal.libraries.kuery.database

import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import libetal.kotlin.debug.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.core.statements.results.Result
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.SQLTimeoutException

class Connector
private constructor(override val dbName: String, val version: Int) : CoreConnector {

    var exception: Exception? = null

    val connection by laziest {
        val connection = try {
            Class.forName("org.sqlite.JDBC")
            val fileName = when (dbName.substringAfterLast(".")) {
                "db", "sqlite" -> dbName
                else -> "$dbName.sqlite"
            }
            DriverManager.getConnection("jdbc:sqlite:$fileName") ?: null
        } catch (e: Exception) {
            exception = e
            null
        } ?: throw RuntimeException("Failed to open database: Error = ${exception?.message}")

        TAG info "Connection to database $dbName established"

        connection
    }

    override fun <R : Result> execute(statement: Statement<R>) {
        TODO("Not yet implemented")
    }

    override fun query(sqlStatement: String): Boolean {
        TODO("Not yet implemented")
    }


    val connectionStatement
        get() = connection.createStatement() ?: throw RuntimeException("Failed to create statement!!")

    override fun query(statement: Create<*, *>): Flow<CreateResult> = flow {
        val error = try {
            connectionStatement.executeUpdate(statement.sql)
            null
        } catch (e: SQLException) {
            e
        } catch (e: SQLTimeoutException) {
            e
        }

        emit(
            CreateResult(
                statement.entity.name,
                statement.type,
                error = error
            )
        )

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

    override fun query(statement: Drop): Flow<DropResult> = flow {
        val error = try {
            connectionStatement.executeUpdate(statement.sql)
            null
        } catch (e: SQLException) {
            e
        } catch (e: SQLTimeoutException) {
            e
        }

        emit(
            DropResult(
                statement.entity.name,
                error = error
            )
        )
    }

    override fun query(statement: Update): Flow<UpdateResult> {
        TODO("Not yet implemented")
    }

    companion object {

        private var instance: Connector? = null

        const val TAG = "Connector"

        operator fun invoke(dbName: String, version: Int): Connector = instance ?: Connector(dbName, version).also {
            instance = it
        }

    }

}