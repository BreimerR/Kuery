package libetal.libraries.kuery.sqlite.core.database

expect class Connector : libetal.libraries.kuery.core.Connector {

    override val database: String

/*    *//**
     * Handles execution of bound statements
     * Should be the final implementation
     **//*
    fun <R : Result> execute(statement: Statement<R>)*/

    companion object {
        operator fun invoke(): libetal.libraries.kuery.core.Connector
    }

}


