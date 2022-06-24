package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.runBlocking
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.mariadb.ConnectorTest.Companion.connector
import libetal.libraries.kuery.mariadb.statements.CreateResult
import libetal.libraries.kuery.mariadb.statements.DeleteResult
import kotlin.test.AfterClass
import kotlin.test.assertNull
import kotlin.test.assertTrue

interface TestCase {

    fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> query(
        statement: Statement<T, E>,
        collector: (libetal.libraries.kuery.core.statements.Result) -> Unit
    ) {
        (connector query statement)(collector)
    }

    infix fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> Statement<T, E>.query(
        collector: (libetal.libraries.kuery.core.statements.Result) -> Unit
    ) {
        (connector query this)(collector)
    }

    operator fun <T> Flow<T>.invoke(collector: (T) -> Unit) = runBlocking {
        this@invoke.collect { value ->
            collector(value)
        }
    }

}