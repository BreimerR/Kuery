package libetal.libraries.kuery.sqlite.core.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import libetal.kotlin.debug.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.kotlin.io.File
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.database.extensions.listeners
import java.sql.*
import libetal.libraries.kuery.core.Connector as CoreConnector

actual class Connector
private constructor(actual override val database: String, version: Int) : CoreConnector {

    var exists = false

    var exception: Exception? = null

    private val connection by laziest({
        if (!exists) listeners.forEach { listener ->
            listener.onCreate(this)
        }
        exists = true
    }) {
        val fileName = when (database.substringAfterLast(".")) {
            "db", "sqlite" -> database
            else -> "$database.sqlite"
        }

        try {

            Class.forName("org.sqlite.JDBC")

            val file = File(fileName).also {
                if (!it.parentFile.exists()) it.parentFile.mkdirs()
            }

            exists = file.exists()

            DriverManager.getConnection("jdbc:sqlite:$fileName") ?: null
        } catch (e: Exception) {
            exception = e
            null
        }?.also {
            TAG info "Connection to database $fileName established"
        } ?: throw RuntimeException("Failed to open database: Error = ${exception?.message}")
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

    actual companion object {

        private var instance: Connector? = null

        const val TAG = "Connector"

        operator fun invoke(dbName: String, version: Int) = instance ?: Connector(dbName, version).also {
            instance = it
        }

        actual operator fun invoke(): libetal.libraries.kuery.core.Connector =
            instance ?: throw RuntimeException("Please call invoke with dbName first")

    }

}