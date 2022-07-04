package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column

class DecoratedExpression<T> : Expression<String> {

    constructor(
        left: Column<T>,
        operator: Operators,
        right: T,
        prefix: String = "",
        postfix: String = ""
    ) : this(left.name, operator, with(left) { right.sqlString }, prefix, postfix)

    constructor(
        column: String,
        operator: Operators,
        value: String,
        prefix: String = "",
        postfix: String = ""
    ) : super(
        column,
        operator.toString(),
        if (value.length > 3 && value.getOrNull(0) == '\'' && value.getOrNull(value.length - 1) == '\'') "'${
            prefix + value.substring(
                1,
                value.length - 1
            ) + postfix
        }'" else value
    )

    override val sql: String by laziest {
        "`$left` $operator $right"
    }

    override val boundSql: String by laziest {
        "`$left` $operator ?"
    }

}