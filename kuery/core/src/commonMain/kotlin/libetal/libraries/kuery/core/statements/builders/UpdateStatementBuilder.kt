package libetal.libraries.kuery.core.statements.builders

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.statements.Update


class UpdateStatementBuilder<T, E : Entity<T>>(val entity: E) : WhereStatementBuilder<T, E, Update>() {

    private val expressions by lazy {
        mutableListOf<SimpleExpression<*>>()
    }

    val expressionsSQL by laziest {
        "SET " + expressions.joinToString(",") { it.toString() }
    }

    infix fun <T> Column<T>.to(value: T) {
        expressions += SimpleExpression(this, Expression.Operators.EQUALS, value)
    }

    override fun build(where: String, boundWhere: String) = Update(where, boundWhere).also { statement ->
        statement.entity = entity
    }

}

