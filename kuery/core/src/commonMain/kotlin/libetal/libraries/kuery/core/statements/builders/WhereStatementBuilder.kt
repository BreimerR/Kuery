package libetal.libraries.kuery.core.statements.builders

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.WhereScope
import libetal.libraries.kuery.core.statements.Statement

abstract class WhereStatementBuilder<T, E : Entity<T>, S : Statement<*>> : EntityStatementBuilder<T, E, S> {

    val columnValues by laziest {
        mutableListOf<Any>()
    }

    abstract fun build(where: String, boundWhere: String, vararg columnValues: Any): S

    infix fun WHERE(expressionBuilder: WhereScope.() -> Expression<*, *>): S =
        WHERE(expressionBuilder(WhereScope()))

    open infix fun WHERE(expression: Expression<*, *>): S = build(
        expression.sql.let { sql ->
            if (sql[0] == '(' && sql.last() == ')') {
                sql.substring(1, sql.length - 1)
            } else sql
        },
        expression.boundSql,
        *(expression.columnValues as List<Any>).toTypedArray()
    )


    infix fun WHERE(boolean: Boolean): S =
        build("$boolean", "?", boolean)

}