package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import java.sql.DriverManager

actual class Connector actual constructor(
    actual val database: String,
    actual val user: String,
    actual val password: String,
    actual val host: String,
    actual val port: UInt
) : libetal.libraries.kuery.core.Connector {

    val connection by laziest {
        DriverManager.getConnection("jdbc:mariadb://$host:$port/$database?user=$user&password=$password")
            ?: throw RuntimeException("Failed to establish connection to database")
    }

    actual infix fun <R : Result> execute(statement: Statement<R>): Flow<R> {
        val execute = query(statement.sql)
        TODO("Not yet implemented")
    }

    actual fun close() {
        connection.close()
    }

    override fun query(sqlStatement: String): Boolean {
        val statement = connection.createStatement()

        val executed = statement.execute(sqlStatement)

        return executed
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