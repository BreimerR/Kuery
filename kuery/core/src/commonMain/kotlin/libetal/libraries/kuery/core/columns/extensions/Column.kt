package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.expressions.Expression

infix fun <T> Column<T>.lessThan(value: T): Expression = Expression("`$identifier` < ${value.sqlString}")

infix fun <T> Column<T>.greaterThan(value: T): Expression = Expression("`$identifier` > ${value.sqlString}")

infix fun <T> Column<T>.lessOrEqual(value: T): Expression = Expression("`$identifier` <= ${value.sqlString}")

infix fun <T> Column<T>.greaterOrEqual(value: T): Expression = Expression("`$identifier` >= ${value.sqlString}")

@Suppress("CovariantEquals")
infix fun <T> Column<T>.equals(value: T): Expression = Expression("`$identifier` == ${value.sqlString}")