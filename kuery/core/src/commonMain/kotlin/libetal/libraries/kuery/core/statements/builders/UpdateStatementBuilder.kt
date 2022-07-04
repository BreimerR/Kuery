package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.UpdateExpression
import libetal.libraries.kuery.core.statements.Update


class UpdateStatementBuilder<T, E : Entity<T>>(entity: E) :
    EntityStatementBuilder<T, E, Update<T, E>>("UPDATE `${entity.name}`", entity = entity) {

    private val expressions by lazy {
        mutableListOf<SimpleExpression<*>>()
    }

    val expressionsSQL
        get() = "SET " + expressions.joinToString(",") { it.toString() }

    infix fun <T> Column<T>.to(value: T) {
        expressions += SimpleExpression(this, Expression.Operators.EQUALS, value)
    }

    override fun build(extras: String) = Update("$sql $extras", entity).also { updateStatement ->
        updateStatement.columns.addAll(columns)
    }

}

