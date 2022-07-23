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
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.SQLTimeoutException
import java.sql.Statement as JvmStatement

class Connector
private constructor(override val dbName: String, val version: Int) : CoreConnector {

    var exception: Exception? = null

    private val connection by laziest {
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

    fun <R : Result> executeUpdate(statement: Statement<R>) = try {
        connectionStatement.executeUpdate(statement.sql)
        null
    } catch (e: SQLException) {
        e
    } catch (e: SQLTimeoutException) {
        e
    }

    /**
     * Feels a bit confusing to utilize
     **/
    private infix fun <R : Result> Statement<R>.executeQuery(then: (ResultSet) -> Unit) = try {
        then(connectionStatement.executeQuery(sql))
        null
    } catch (e: SQLException) {
        e
    } catch (e: SQLTimeoutException) {
        e
    }

    override fun query(statement: Create<*, *>): Flow<CreateResult> = flow {
        val error = executeUpdate(statement)

        emit(
            CreateResult(
                statement.entity.name,
                statement.type,
                error = error
            )
        )

    }

    override fun query(statement: Select): Flow<SelectResult> = flow {
        var resultSet: ResultSet? = null

        val error = statement executeQuery {
            resultSet = it
        }

        resultSet?.let { set ->

            try {
                val columns = statement.columns
                val columnsSize = columns.size
                var r = 0

                while (set.next()) {
                    val row = buildList {
                        var i = 0
                        while (i < columnsSize) {
                            val column = columns[i]
                            this += column parse set.getString(column.name)
                            i++
                        }
                        r++
                    }

                    emit(
                        SelectResult(
                            row,
                            error,
                            *columns
                        )
                    )

                }

            } catch (e: SQLException) {
                throw RuntimeException(e)
            }

        }

    }

    override fun query(statement: Delete): Flow<DeleteResult> {
        TODO("Not yet implemented")
    }

    override fun query(statement: Insert): Flow<InsertResult> = flow {
        val error = executeUpdate(statement)

        emit(
            InsertResult(
                into = statement.entity.name,
                error = error
            )
        )
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