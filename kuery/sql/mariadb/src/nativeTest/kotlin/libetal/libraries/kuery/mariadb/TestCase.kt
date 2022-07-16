package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.mariadb.ConnectorTest.Companion.connector

interface TestCase {

    infix fun Create<*, *>.query(
        collector: CreateResult.() -> Unit
    ) = connector.query(this)(collector)

    infix fun Select.query(
        collector: SelectResult.() -> Unit
    ) = connector.query(this)(collector)

    infix fun Insert.query(
        collector: InsertResult.() -> Unit
    ) = connector.query(this)(collector)

    infix fun Delete.query(
        collector: DeleteResult.() -> Unit
    ) = connector.query(this)(collector)

    infix fun Drop.query(
        collector: DropResult.() -> Unit
    ) = connector.query(this)(collector)

    infix fun Update.query(
        collector: UpdateResult.() -> Unit
    ) = connector.query(this)(collector)

    operator fun <T> Flow<T>.invoke(collector: (T) -> Unit) = runBlocking {
        this@invoke.collect { value ->
            collector(value)
        }
    }


    fun test() {
        val ubyte: UInt = 0u
        val m = 1
        val isZero = ubyte == 0u
    }

}