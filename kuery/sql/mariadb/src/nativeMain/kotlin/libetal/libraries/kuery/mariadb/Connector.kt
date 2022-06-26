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
import libetal.libraries.kuery.core.statements.results.CreateResult
import libetal.libraries.kuery.core.statements.results.DeleteResult
import libetal.libraries.kuery.core.statements.results.InsertResult
import libetal.libraries.kuery.core.statements.results.SelectResult
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
    actual infix fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> execute(statement: Statement<T, E>): Flow<T> =
        TODO("Object mapping can happen here")


    /**
     * The query runs on a single thread
     * and until the results are completely
     * read you can't pollute result.
     *
     * In the case of specific columns selected a Map<TableName,Map<Column,Value>>
     * Should be utilized. But if the unspecified columns are nullable then T being return storage should be used
     **/
    infix fun <Class, E : libetal.libraries.kuery.core.entities.Entity<Class>> query(
        statement: Statement<Class, E>
    ) = flow {

        if (query(statement.toString())) {
            when (statement) {
                is Create<Class, E> -> { // CREATE doesn't have results
                    emit(
                        CreateResult(
                            name = statement.entity.name,
                            type = statement.type,
                            null
                        )
                    )
                    return@flow
                }
                is Delete<Class, E> -> { // DELETE doesn't have results
                    emit(
                        DeleteResult(
                            table = statement.entity.name,
                            null
                        )
                    )
                    return@flow
                }
                is Select<Class, E> -> { // Select has results
                    val results = connection.useResult() ?: throw NullPointerException("Unexpected null results")

                    val numColumns = connection.fieldCount.toInt()

                    while (true) {
                        val row = results.row ?: break
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

                            emit(
                                SelectResult(
                                    table = column.table?.toKString() ?: throw NullPointerException("Failed to read table"),
                                    columnName = column.name?.toKString()
                                        ?: throw NullPointerException("Unexpected null result"),
                                    value = value
                                )
                            )
                            i++
                        }

                        // emit the whole row instead

                    }

                    mysql_free_result(results)

                }

                is Insert<Class, E> -> {
                    emit(
                        InsertResult(
                            into = statement.entity.name
                        )
                    )
                }

            }

        } else throw MariaDbException(connection.errorCode, connection.errorMessage)

    }


    @Suppress("unused")
    actual fun close() {
        connection.close()
    }

}