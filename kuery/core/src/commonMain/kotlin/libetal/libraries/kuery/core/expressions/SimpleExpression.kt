@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn

class SimpleExpression<T> : Expression<BaseColumn<T>, T> {

    constructor(
        left: BaseColumn<T>,
        operator: Operators,
        right: T
    ) : super(left, operator, right)


    override val sql: String
        get() = "${left.identifier} $operator ${with(left) { right.sqlString }}"

    override val boundSql: String by laziest {
        "${left.identifier} $operator ?"
    }

}


class BooleanExpression<T> : Expression<BaseColumn<T>, Boolean> {

    constructor(
        left: BaseColumn<T>,
        operator: Operators,
        right: Boolean
    ) : super(left, operator, right)


    override val sql: String
        get() = "${left.identifier} $operator $right"

    override val boundSql: String by laziest {
        "${left.identifier} $operator ?"
    }

}









