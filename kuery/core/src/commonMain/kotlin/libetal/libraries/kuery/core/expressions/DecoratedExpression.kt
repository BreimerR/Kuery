package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.statements.parseToSql

/**TODO
 * SQL Concat value to prefix and postfix
 **/
class DecoratedExpression<T : Any> : DecoratedExpressions<Column<T>, T> {

    constructor(
        left: Column<T>,
        operator: Operators,
        right: T,
        prefix: String = "",
        postfix: String = ""
    ) : super(left, operator, right, prefix, postfix)

    override val sql: String by laziest {
        "`$left` $operator ${left parseToSql right}"
    }

    override val boundSql: String by laziest {
        "`$left` $operator ?"
    }

    override val columnValues by laziest {
        listOf(
            right
        )
    }

}

