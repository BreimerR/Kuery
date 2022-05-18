package libetal.libraries.kuery.mariadb

import kotlinx.cinterop.get
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kuery.interop.mariadb.mysql_free_result
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.Create
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.mariadb.interop.*
import kotlin.coroutines.CoroutineContext

actual class Connector actual constructor(
    actual val database: String,
    actual val user: String,
    actual val password: String,
    actual val host: String,
    actual val port: UInt
) {

    val connection by laziest {
        Mysql.realConnect(
            host,
            user,
            password,
            database,
            port
        )
    }

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
    infix fun <Class, E : libetal.libraries.kuery.core.entities.Entity<Class>> query(statement: Statement<Class, E>) =
        flow {

            val executed = connection.query(statement.toString())

            if (executed == 0) {
                when (statement) {
                    is Create<Class, E> -> { // CREATE doesn't have results
                        emit("Created" to "Success")
                        return@flow
                    }
                }
                val result = connection.useResult() ?: throw NullPointerException("Unexpected null results")

                val numColumns = connection.fieldCount.toInt()

                while (true) {
                    val row = result.row ?: break
                    var i = 0
                    while (i < numColumns) {
                        val column = (result fetchFieldDirect i.toUInt())?.pointed ?: break
                        val columnTable = column.table?.toKString() ?: throw NullPointerException("Failed to read table")
                        val columnName = column.name?.toKString() ?: throw NullPointerException("Unexpected null result")
                        val columnValue = row[i]?.toKString() ?: throw NullPointerException("Unexpected null result")

                        emit(columnName to columnValue)
                        i++
                    }

                    mysql_free_result(result)

                }


            } else {
                throw RuntimeException("Connection Error:${connection.errorCode} ${connection.errorMessage}")
            }
        }

    @Suppress("unused")
    actual fun close() {
        connection.close()
    }

}