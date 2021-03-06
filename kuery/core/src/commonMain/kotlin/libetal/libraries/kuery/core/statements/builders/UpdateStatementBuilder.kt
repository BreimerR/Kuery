package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Update
import kotlin.to as kotlinTo

class UpdateStatementBuilder<T, E : Entity<T>>(val entity: E) : WhereStatementBuilder<T, E, Update>() {

    private val expressions by lazy {
        mutableListOf<Pair<EntityColumn<*>, *>>()
    }

    infix fun <T> EntityColumn<T>.to(value: T) {
        expressions.add(this kotlinTo value)
    }


    override fun build(where: String, boundWhere: String) = Update(where, boundWhere).also { statement ->
        statement.entity = entity
        statement.set.addAll(expressions)
    }

}

