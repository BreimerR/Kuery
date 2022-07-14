package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.statements.Select

class DecoratedStatementExpression<T : Any> : DecoratedExpressions<Column<T>, Select> {
    constructor(
        left: Column<T>,
        operator: Operators,
        right: Select,
        prefix: String = "",
        postfix: String = ""
    ) : super(left, operator, right, prefix, postfix)

    override val sql: String by laziest {
        "${left.identifier} $operator (${right.sql})"
    }

    override val boundSql: String by laziest {
        "${left.identifier} $operator (${right.boundSql})"
    }
    override val columnValues: List<*> by laziest {
        buildList {
            addAll(
                right.columnValues
            )
        }
    }
}