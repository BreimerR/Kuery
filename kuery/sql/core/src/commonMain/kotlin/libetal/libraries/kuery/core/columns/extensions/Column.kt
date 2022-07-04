package libetal.libraries.kuery.core.columns.extensions

// @Suppress("CovariantEquals")
// infix fun <T> Column<T>.equals(value: T): Expression = Expression("`$identifier` == ${value.sqlString}")