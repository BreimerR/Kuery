package libetal.libraries.kuery.mariadb

import kotlinx.cinterop.get
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kuery.interop.mariadb.mysql_free_result
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.mariadb.exceptions.MariaDbException
import libetal.libraries.kuery.mariadb.interop.*

actual class Connector actual constructor(
    actual val database: String,
    actual val user: String,
    actual val password: String,
    actual val host: String,
    actual val port: UInt
) : libetal.libraries.kuery.core.Connector {

    private val connection by laziest {
        Mysql.realConnect(
            host,
            user,
            password,
            database,
            port
        )
    }

    override fun query(sqlStatement: String) = connection.query(sqlStatement) == 0

    /**
     * Don't use flows if the function isn't suspended
     * Not really usefull that way
     **/
    actual infix fun <R : Result> execute(statement: Statement<R>): Flow<R> =
        TODO("Object mapping can happen here")

    /**
     * The query runs on a single thread
     * and until the results are completely
     * read you can't pollute result.
     *
     * In the case of specific columns selected a Map<TableName,Map<Column,Value>>
     * Should be utilized. But if the unspecified columns are nullable then T being return storage should be used
     **/
    override infix fun query(statement: Create<*, *>) = flow {
        query(statement.toString())
        @Suppress("UNCHECKED_CAST")
        emit(
            CreateResult(
                name = "TODO(Definately not correct)",
                type = statement.type,
                null
            )
        )

    }

    override infix fun query(statement: Select) = flow {
        query(statement.toString())

        val results = connection.useResult() ?: throw NullPointerException("Unexpected null results")

        val numColumns = connection.fieldCount.toInt()



        while (true) {
            val row = results.row ?: break
            val emission = mutableListOf<Any?>()
            var i = 0
            while (i < numColumns) {
                val column = (results fetchFieldDirect i.toUInt())?.pointed ?: break
                val statementColumn = statement.columns[i]
                val value = row[i]?.toKString()

                val statementValue = value?.let {
                    statementColumn.parse(it)
                } ?: if (statementColumn.nullable)
                    null
                else
                    throw RuntimeException("Null Received for non null column `${statement.entity.name}`.${statementColumn.name}")

                emission += statementValue
                i++
            }

            @Suppress("UNCHECKED_CAST")
            emit(
                SelectResult(
                    row = emission
                )
            )
        }

        mysql_free_result(results)

    }

    override infix fun query(statement: Delete) = flow {
        query(statement.toString())
        @Suppress("UNCHECKED_CAST")
        emit(
            DeleteResult(
                table = statement.entity.name,
                null
            )
        )
    }

    override infix fun query(statement: Insert) = flow {
        query(statement.toString())
        @Suppress("UNCHECKED_CAST")
        emit(
            InsertResult(
                into = statement.entity.name
            )
        )
    }

    override infix fun query(statement: Drop) = flow {
        query(statement.toString())
        @Suppress("UNCHECKED_CAST")
        emit(
            DropResult(
                table = statement.entity.name,
                null
            )
        )
    }

    override infix fun query(statement: Update) = flow {
        query(statement.toString())
        @Suppress("UNCHECKED_CAST")
        emit(
            UpdateResult(
                table = statement.entity.name,
                null
            )
        )
    }

    @Suppress("unused")
    actual fun close() {
        connection.close()
    }

}