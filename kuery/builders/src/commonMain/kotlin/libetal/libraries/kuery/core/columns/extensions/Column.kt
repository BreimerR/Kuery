package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.Expression.Operators.*
import libetal.libraries.kuery.core.expressions.NullExpression
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.StatementExpression
import libetal.libraries.kuery.core.statements.Select

infix fun <T> BaseColumn<T>.lessThan(value: T) =
    SimpleExpression(this, LESSER, value)

// TODO: Doesn't work for longs
infix fun <T> BaseColumn<T>.greaterThan(value: T) =
    SimpleExpression(this, GREATER, value)

// TODO: Doesn't work for longs
infix fun <T> BaseColumn<T>.greaterThan(value: Select) =
    StatementExpression(this, GREATER, value)

infix fun <T> BaseColumn<T>.lessOrEqual(value: T) =
    SimpleExpression(this, LESS_OR_EQUALS, value)

infix fun <T> BaseColumn<T>.greaterOrEqual(value: T) =
    SimpleExpression(this, GREATER_OR_EQUALS, value)

@Suppress("CovariantEquals")
infix fun <T> BaseColumn<T>.equals(value: T) =
    equals(value, ::SimpleExpression)

@Suppress("CovariantEquals")
infix fun <T> BaseColumn<T>.equals(value: Boolean) =
    NullExpression(this, if (value) NOT_EQUALS else EQUALS)

infix fun <T> BaseColumn<T>.equals(value: Select) =
    equals(value, ::StatementExpression)

internal fun <C, T, F> BaseColumn<C>.equals(value: T, initializer: (BaseColumn<C>, Expression.Operators, T) -> F) =
    initializer(this, EQUALS, value)