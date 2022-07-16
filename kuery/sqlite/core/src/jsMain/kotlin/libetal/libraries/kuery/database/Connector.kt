package libetal.libraries.kuery.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Statement

class Connector(override val dbName:String) : libetal.libraries.kuery.sqlite.core.database.Connector {

    override suspend fun executeSQL(statement: Statement<*>): Unit = withContext(Dispatchers.Default) {
        TODO("")
    }

    override fun <T, E : Entity<T>, S : Statement<T, E>> execute(statement: S) {
        TODO("")
    }

    companion object {

        val INSTANCE: Connector
            get() = TODO("Not yet implemented")

        operator fun invoke(): Connector {
            TODO("Not yet implemented")
        }

    }

}