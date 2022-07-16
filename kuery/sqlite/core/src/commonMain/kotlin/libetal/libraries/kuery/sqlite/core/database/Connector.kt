package libetal.libraries.kuery.sqlite.core.database

import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.core.statements.results.Result

interface Connector : libetal.libraries.kuery.core.Connector {

    val dbName: String

    /**
     * Handles execution of bound statements
     * Should be the final implementation
     **/
    fun <R : Result> execute(statement: Statement<R>)

}


