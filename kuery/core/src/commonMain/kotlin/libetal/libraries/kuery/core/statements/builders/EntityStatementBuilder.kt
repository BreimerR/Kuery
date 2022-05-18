package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.Statement

abstract class EntityStatementBuilder<T, E : Entity<T>, S : Statement<T, E>>(
    override val sql: String,
    vararg val columns: Column<*>,
    val entity: E
) : StatementBuilder {
    abstract fun build(extras: String): S

    fun <T, E : Entity<T>> E.buildFrom(where: String) = build("FROM `${name}` WHERE $where")

}