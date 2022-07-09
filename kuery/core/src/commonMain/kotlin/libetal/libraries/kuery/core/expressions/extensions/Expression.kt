@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.JoinedExpression
import libetal.libraries.kuery.core.expressions.OperatorScope

infix fun Expression<*, *>.AND(expressionBuilder: OperatorScope.() -> Unit): Expression<*, *> = run {
    val scope = OperatorScope(this, "AND")
    expressionBuilder(scope)
    scope.expression
}


infix fun Expression<*, *> .AND(expression: Expression<*, *> ): Expression<*, *>  = JoinedExpression(
    this,
    Expression.Operators.AND,
    expression
)

infix fun Expression<*, *> .OR(expression: Expression<*, *> ): Expression<*, *>  = JoinedExpression(
    this,
    Expression.Operators.OR,
    expression
)

infix fun Expression<*, *> .OR(expressionBuilder: OperatorScope.() -> Unit) = run {
    val scope = OperatorScope(this, "OR")
    expressionBuilder(scope)
    scope.expression
}

// fun <T, C : Column<T>> C.expressionBuilder(value: T, operator: String) = SimpleExpression<T>(name, operator, value)
fun <T, C : Column<T>> C.expressionBuilder(value: T, operator: Expression.Operators) =
    SimpleExpression<T>(name, operator, value.sqlString)