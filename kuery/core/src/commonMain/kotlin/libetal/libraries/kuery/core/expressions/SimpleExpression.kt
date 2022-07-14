@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn

class SimpleExpression<T> : Expression<EntityColumn<T>, T> {

    constructor(
        left: EntityColumn<T>,
        operator: Operators,
        right: T
    ) : super(left, operator, right)


    override val sql: String
        get() = "${left.identifier} $operator ${with(left) { right.sqlString }}"

    override val boundSql: String by laziest {
        "${left.identifier} $operator ?"
    }
    override val columnValues: List<*> by laziest {
        listOf(
            right
        )
    }

}


class BooleanExpression<T> : Expression<EntityColumn<T>, Boolean> {

    constructor(
        left: EntityColumn<T>,
        operator: Operators,
        right: Boolean
    ) : super(left, operator, right)


    override val sql: String
        get() = "${left.identifier} $operator $right"

    override val boundSql: String by laziest {
        "${left.identifier} $operator $right"
    }

    override val columnValues: List<*> by laziest {
        listOf<Boolean>()
    }

}









