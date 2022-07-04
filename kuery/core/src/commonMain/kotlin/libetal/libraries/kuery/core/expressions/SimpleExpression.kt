@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column

class SimpleExpression<T> : Expression<String> {

    constructor(
        left: Column<T>,
        operator: Operators,
        right: T
    ) : this(left.name, operator.toString(), with(left) { right.sqlString })

    private constructor(column: String, operator: String, right: String) : super(column, operator, right)

    constructor(column: String, operator: Operators, value: String) : this(column, operator.toString(), value)

    override val sql: String
        get() = "`$left` $operator $right"

    override val boundSql: String by laziest {
        "`$left` $operator ?"
    }
    override val columnValues: List<Pair<String, String>> by laziest {
        listOf(
            left to right
        )
    }

}









