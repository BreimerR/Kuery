@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.builders.InsertStatementBuilder

infix fun <T, E : Entity<T>> INSERT.INTO(entity: E) =
    InsertStatementBuilder(entity)

infix fun <T, E : Entity<T>> InsertStatementBuilder<T, E>.VALUES(valueBuilder: InsertStatementBuilder<T, E>.(E) -> Unit) = let {
    it.valueBuilder(entity)
    it.build("$columnsSQL VALUES $valuesSql")
}

infix operator fun <T, E : Entity<T>> InsertStatementBuilder<T, E>.invoke(valueBuilder: InsertStatementBuilder<T, E>.(E) -> Unit) =
    apply {
        valueBuilder(entity)
    }
