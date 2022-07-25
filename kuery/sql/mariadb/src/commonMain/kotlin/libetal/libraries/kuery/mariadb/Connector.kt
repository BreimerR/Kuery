package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*

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
    override val database: String
    val port: UInt

    infix fun <R : Result> execute(statement: libetal.libraries.kuery.core.statements.Statement<R>): Flow<R>

    fun close()

}