package libetal.libraries.kuery.core

import kotlinx.coroutines.flow.Flow
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*

interface Connector {

    val name: String?
    fun query(sqlStatement: String): Boolean

    infix fun query(statement: Create<*, *>): Flow<CreateResult>

    infix fun query(statement: Select): Flow<SelectResult>

    infix fun query(statement: Delete): Flow<DeleteResult>

    infix fun query(statement: Insert): Flow<InsertResult>

    infix fun query(statement: Drop): Flow<DropResult>

    infix fun query(statement: Update): Flow<UpdateResult>

}