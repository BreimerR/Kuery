package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn

class BooleanExpression<T>(left: BaseColumn<T>, operator: Operators, right: Boolean) :
    Expression<BaseColumn<T>, Boolean>(left, operator, right) {

    override val sql: String
        get() = "${left.identifier} $operator $right"

    override val boundSql: String by laziest {
        "${left.identifier} $operator ?"
    }

}