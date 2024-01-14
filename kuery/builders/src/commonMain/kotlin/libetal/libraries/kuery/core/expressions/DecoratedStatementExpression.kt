package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.statements.Select

class DecoratedStatementExpression<T : Any> : DecoratedExpressions<BaseColumn<T>, Select> {
    constructor(
        left: BaseColumn<T>,
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
    // TODO NEED TO Concatnate this string such that CONCAT(CONCAT('\'',SELECT SOME_VALUE FROM SOME_TABLE WHERE SOME_EXPRESSION),'\'')
    override val columnValues by laziest {
        buildList {
            addAll(
                right.arguments
            )
        }
    }
}
