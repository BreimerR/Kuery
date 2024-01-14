package libetal.libraries.kuery.sqlite.core.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import libetal.kotlin.log.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.kotlin.io.File
import libetal.kotlin.io.exists
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.database.extensions.listeners
import java.io.InputStream
import java.io.Reader
import kotlin.Byte
import java.math.BigDecimal
import java.net.URL
import java.sql.*

actual class Connector
private constructor(path: String?, override val name: String?, version: Int) : KSQLiteConnector {

    var exists = false

    var exception: Exception? = null

    private val connection by laziest({
        if (!exists) listeners.forEach { listener ->
            listener.onCreate(this)
        }
        exists = true
    }) {
        val fileName = "$path/$name"

        try {

            Class.forName("org.sqlite.JDBC")

            val file = File(fileName).also {
                if (!it.parentFile.exists()) it.parentFile.mkdirs()
            }

            exists = file.exists

            DriverManager.getConnection("jdbc:sqlite:$fileName") ?: null
        } catch (e: Exception) {
            exception = e
            null
        }?.also {
            TAG info "Connection to database $fileName established"
        } ?: throw RuntimeException("Failed to open database: $fileName Error = ${exception?.message}")
    }

    private constructor(name: String?, version: Int) : this(
        name?.let { KSQLiteConnector.resolveName(name).first },
        name?.let { KSQLiteConnector.resolveName(name).second },
        version
    )


    override fun query(sqlStatement: String): Boolean {
        TODO("Not yet implemented")
    }

    val connectionStatement
        get() = connection.createStatement() ?: throw RuntimeException("Failed to create statement!!")

    inline fun <reified T> isInstance(item: Any) = item is T

    fun <R : Result> executeWithNoResult(statement: Statement<R>, vararg arguments: Any) = try {
        TAG info statement.sql

        @Suppress("SqlSourceToSinkFlow")
        when (statement) {
            is ArgumentsStatement<R> -> {
                TAG info statement.boundSql

                val preparedStatement = connection.prepareStatement(statement.boundSql)

                preparedStatement.apply {

                    for ((i, argument) in statement.arguments.withIndex()) {
                        TAG info "Argument is $i $argument"
                        val c = i + 1

                        when (argument) {
                            null -> setNull(c, Types.NULL)
                            is Boolean -> setBoolean(c, argument)
                            is Byte -> setByte(c, argument)
                            is Short -> setShort(c, argument)
                            is Int -> setInt(c, argument)
                            is Long -> setLong(c, argument)
                            is Float -> setFloat(c, argument)
                            is Double -> setDouble(c, argument)
                            is BigDecimal -> setBigDecimal(c, argument)
                            is String -> setString(c, argument)
                            is ByteArray -> setBytes(c, argument)
                            is Date -> setDate(c, argument)
                            is Time -> setTime(c, argument)
                            is Timestamp -> setTimestamp(c, argument)
                            is Ref -> setRef(c, argument)
                            is Blob -> setBlob(c, argument)
                            is Clob -> setClob(c, argument)
                            is java.sql.Array -> setArray(c, argument)
                            is URL -> setURL(c, argument)
                            is RowId -> setRowId(c, argument)
                            is Reader -> setCharacterStream(c, argument)
                            is SQLXML -> setSQLXML(c, argument)
                            else -> setObject(c, argument)
                            // TODO: is InputStream -> setAsciiStream(i, argument)
                            // TODO: is CharacterStream -> setCharacterStream(i, argument)
                            // TODO: is NString -> setNString(i, argument)
                            // TODO: is NCharacterStream -> setNCharacterStream(i, argument)
                            // TODO: is InputStream -> setAsciiStream(i, argument)
                            // TODO: is BinaryStream -> setBinaryStream(i, argument)
                            // TODO: is BinaryStream -> setBinaryStream(i, argument)
                        }
                    }
                }

                preparedStatement.execute()
            }

            else -> connectionStatement.executeUpdate(statement.sql)
        }

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
        val error = executeWithNoResult(statement)

        emit(
            CreateResult(
                statement.entity.name,
                statement.type,
                error = error
            )
        )

    }

    override fun query(statement: Select): Flow<SelectResult> = flow {
        query(statement, ::emit)
    }

    override suspend fun query(statement: Select, onExec: suspend SelectResult.() -> Unit) {

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
                    val results = buildMap {
                        var i = 0
                        while (i < columnsSize) {
                            val column = columns[i]
                            this[column] = set.getString(column.name) ?: null
                            i++
                        }
                        r++
                    }

                    onExec(
                        SelectResult(
                            results,
                            error
                        )
                    )

                }

            } catch (e: SQLException) {
                throw RuntimeException(e)
            }

        }
    }

    override suspend fun execute(statement: Insert, onExec: suspend SelectResult.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun query(statement: Delete): Flow<DeleteResult> = flow {
        val error = executeWithNoResult(statement)

        emit(
            DeleteResult(
                statement.entity.name,
                error = error
            )
        )
    }

    override fun query(statement: Insert): Flow<InsertResult> = flow {
        val error = executeWithNoResult(statement)

        emit(
            InsertResult(
                into = statement.entity.name,
                error = error
            )
        )
    }

    override fun query(statement: Drop): Flow<DropResult> = flow {
        TAG info statement.sql
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

    override fun query(statement: Update): Flow<UpdateResult> = flow {
        val error = executeWithNoResult(statement)

        emit(
            UpdateResult(
                statement.entity.name,
                error
            )
        )

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

    actual operator fun Kuery.invoke() {
        init()
    }

}
