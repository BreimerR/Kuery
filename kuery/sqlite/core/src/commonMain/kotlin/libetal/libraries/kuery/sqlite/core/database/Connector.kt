package libetal.libraries.kuery.sqlite.core.database

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Statement

interface Connector {

    val dbName:String

    /**
     * Simple sql execution from a statement
     * Might be removed in the future
     **/
    suspend fun executeSQL(statement: Statement<*, *>)

    /**
     * Handles execution of bound statements
     * Should be the final implementation
     **/
    fun <T, E : Entity<T>, S : Statement<T, E>> execute(statement: S)

}


