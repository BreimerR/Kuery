package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.expressions.Expression

// @Suppress("CovariantEquals")
// infix fun <T> Column<T>.equals(value: T): Expression = Expression("`$identifier` == ${value.sqlString}")