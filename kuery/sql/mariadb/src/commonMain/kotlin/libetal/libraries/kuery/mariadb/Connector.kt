package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow

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

    infix fun <T> execute(statement: libetal.libraries.kuery.core.statements.Statement): Flow<T>

    fun close()

}