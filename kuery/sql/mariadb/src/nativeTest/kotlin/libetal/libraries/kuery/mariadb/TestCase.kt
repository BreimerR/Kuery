package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.core.statements.results.Result
import libetal.libraries.kuery.mariadb.ConnectorTest.Companion.connector

interface TestCase {

    fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> query(
        statement: Statement<T, E>,
        collector: (Result) -> Unit
    ) {
        (connector query statement)(collector)
    }

    infix fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> Statement<T, E>.query(
        collector: (Result) -> Unit
    ) {
        (connector query this)(collector)
    }

    operator fun <T> Flow<T>.invoke(collector: (T) -> Unit) = runBlocking {
        this@invoke.collect { value ->
            collector(value)
        }
    }

}