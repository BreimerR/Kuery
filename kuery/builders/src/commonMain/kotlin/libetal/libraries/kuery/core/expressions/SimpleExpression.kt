package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn

class SimpleExpression<T>(left: BaseColumn<T>, operator: Operators, right: T) :
    Expression<BaseColumn<T>, T>(left, operator, right) {

    override val sql: String
        get() = "${left.identifier} $operator ${with(left) { right.sqlString }}"

    override val boundSql: String by laziest {
        "${left.identifier} $operator ?"
    }

}












