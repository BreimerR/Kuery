package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Update

class UpdateStatementBuilder<T, E : Entity<T>>(val entity: E) : WhereStatementBuilder<T, E, Update>() {

    private val expressions by lazy {
        mutableListOf<Pair<BaseColumn<*>, *>>()
    }

    infix fun <T> BaseColumn<T>.set(value: T) {
        expressions.add(this to value)
    }

    override fun build(
        where: String,
        boundWhere: String,
        vararg columnValues: Any
    ) = Update(where, boundWhere, *columnValues).also { statement ->
        statement.entity = entity
        statement.set.addAll(expressions)
    }

}

