@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.Insert
import libetal.libraries.kuery.core.statements.builders.InsertStatementBuilder
import libetal.libraries.kuery.core.tableEntities

infix fun <T, E : Entity<T>> INSERT.INTO(entity: E) =
    InsertStatementBuilder(entity, *(tableEntities[entity] ?: throw RuntimeException("Invalid column")).toTypedArray())

infix fun <T, E : Entity<T>> INSERT.INTO(statementBuilder: InsertStatementBuilder<T, E>): InsertStatementBuilder<T, E> =
    statementBuilder

fun <T, E : Entity<T>> InsertStatementBuilder<T, E>.VALUES(vararg inserts: List<Any>): Insert = TODO("")

infix fun <T, E : Entity<T>> InsertStatementBuilder<T, E>.VALUES(valuesBuilder: Insert.() -> Unit): Insert {
    val insert = build()
    valuesBuilder(insert)
    return insert
}

infix operator fun <T, E : Entity<T>> InsertStatementBuilder<T, E>.invoke(valueBuilder: InsertStatementBuilder<T, E>.(E) -> Unit) =
    apply {
        valueBuilder(entity)
    }
