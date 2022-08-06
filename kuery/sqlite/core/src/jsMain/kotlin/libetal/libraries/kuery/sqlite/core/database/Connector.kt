package libetal.libraries.kuery.sqlite.core.database

import kotlinx.coroutines.flow.Flow
import libetal.libraries.kuery.core.Connector as CoreConnector
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.sqlite.core.Kuery

actual class Connector(actual override val database: String) : CoreConnector {

    /*actual fun <R : Result> execute(statement: Statement<R>) {
        TODO("Not yet implemented")
    }*/

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


    actual companion object {

        actual operator fun invoke(): CoreConnector {
            TODO("Not yet implemented")
        }

    }

    actual operator  fun Kuery.invoke() {
        init()
    }

}