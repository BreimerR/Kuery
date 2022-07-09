package libetal.libraries.kuery.database

import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Statement
import java.sql.Connection
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

    override suspend fun executeSQL(statement: Statement<*, *>): Unit = withContext(Dispatchers.IO) {
        execute(statement)
    }

    override fun <T, E : Entity<T>, S : Statement<T, E>> execute(statement: S) {
        val stmnt = connection.createStatement()
        stmnt.execute(statement.sql)
    }

    companion object {

        private var instance: Connector? = null

        operator fun invoke(dbName: String, version: Int): Connector = instance ?: Connector(dbName, version).also {
            instance = it
        }

    }

}