package libetal.libraries.kuery.core.statements.builders

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.WhereScope
import libetal.libraries.kuery.core.statements.Statement

abstract class WhereStatementBuilder<T, E : Entity<T>, S : Statement> : EntityStatementBuilder<T, E, S> {

    val columnValues by laziest {
        mutableListOf<Any>()
    }

    abstract fun build(where: String, boundWhere: String): S

    infix fun WHERE(expressionBuilder: WhereScope.() -> Expression<*, *>): S =
        WHERE(expressionBuilder(WhereScope()))

    infix fun WHERE(expression: Expression<*, *>): S =
        build(expression.sql, expression.boundSql)

    infix fun WHERE(boolean: Boolean): S =
        TODO("")

}