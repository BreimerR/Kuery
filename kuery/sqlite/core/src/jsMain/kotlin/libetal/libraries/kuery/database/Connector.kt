package libetal.libraries.kuery.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.core.statements.results.Result

class Connector(override val dbName:String) : libetal.libraries.kuery.sqlite.core.database.Connector {

    companion object {

        val INSTANCE: Connector
            get() = TODO("Not yet implemented")

        operator fun invoke(): Connector {
            TODO("Not yet implemented")
        }

    }

    override fun <R : Result> execute(statement: Statement<R>) {
        TODO("Not yet implemented")
    }

    override fun query(sqlStatement: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(statement: Create<*, *>): Flow<CreateResult> {
        TODO("Not yet implemented")
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