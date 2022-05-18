@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.OperatorScope

val Expression.AndOperatorScope
    get() = OperatorScope(this, "AND")

val Expression.OrOperatorScope
    get() = OperatorScope(this, "OR")

infix fun Expression.AND(expressionBuilder: OperatorScope.() -> Unit): Expression = apply {
    val scope = AndOperatorScope
    expressionBuilder(scope)
    sql = scope.sql
}

infix fun Expression.OR(expression: OperatorScope.() -> Unit): Expression = apply {
    val scope = OrOperatorScope
    expression(scope)
    sql = scope.sql
}

fun <T, C : Column<T>> C.expressionBuilder(value: T, operator: String) = Expression(identifier, operator, value.sqlString)
fun <T, C : Column<T>> C.expressionBuilder(value: T, operator: Expression.Operator) =
    Expression(identifier, operator, value.sqlString)