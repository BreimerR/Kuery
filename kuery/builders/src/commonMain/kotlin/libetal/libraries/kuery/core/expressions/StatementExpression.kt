package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.statements.Select

class StatementExpression<T>(left: BaseColumn<T>, operator: Operators, right: Select) :
    Expression<BaseColumn<T>, Select>(left, operator, right) {

    override val sql: String by laziest {
        "${left.identifier} = (${right.sql})"
    }

    override val boundSql: String by laziest {
        "${left.identifier} = (${right.boundSql})"
    }

    override val columnValues: List<*> by laziest {
        buildList {
            addAll(
                right.arguments
            )
        }
    }

}
