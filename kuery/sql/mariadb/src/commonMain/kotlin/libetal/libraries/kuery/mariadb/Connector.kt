package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import libetal.libraries.kuery.mariadb.statements.Statement

expect class Connector(
    database: String = "test",
    user: String = "test",
    password: String = "",
    host: String = "localhost",
    port: UInt = 3306u
) : libetal.libraries.kuery.core.Connector {
    val host: String
    val user: String
    val password: String
    val database: String
    val port: UInt

    infix fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> execute(statement: libetal.libraries.kuery.core.statements.Statement<T, E>): Flow<T>

    fun close()

}