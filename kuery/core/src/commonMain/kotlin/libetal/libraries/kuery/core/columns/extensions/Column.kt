package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.Expression.Operators.*
import libetal.libraries.kuery.core.expressions.SimpleExpression

infix fun <T> Column<T>.lessThan(value: T) =
    SimpleExpression(this, LESSER, value)

infix fun <T> Column<T>.greaterThan(value: T) =
    SimpleExpression(this, GREATER, value)

infix fun <T> Column<T>.lessOrEqual(value: T) =
    SimpleExpression(this, LESS_OR_EQUALS, value)

infix fun <T> Column<T>.greaterOrEqual(value: T) =
    SimpleExpression(this, GREATER_OR_EQUALS, value)

@Suppress("CovariantEquals")
infix fun <T> Column<T>.equals(value: T) =
    SimpleExpression(this, EQUALS, value)