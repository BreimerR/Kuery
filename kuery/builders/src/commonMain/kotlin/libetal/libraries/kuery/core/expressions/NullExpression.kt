package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn

class NullExpression<T>(left: BaseColumn<T>, operator: Operators) :
    Expression<BaseColumn<T>, Any?>(left, operator, null) {

    override val sql: String
        get() = "${left.identifier} $operator null"

    override val boundSql: String by laziest {
        "${left.identifier} $operator ?"
    }

}