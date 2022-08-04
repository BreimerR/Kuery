package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.statements.parseToSql

/**TODO
 * SQL Concat value to prefix and postfix
 **/
class DecoratedExpression<T : Any> : DecoratedExpressions<BaseColumn<T>, T> {

    constructor(
        left: BaseColumn<T>,
        operator: Operators,
        right: T,
        prefix: String = "",
        postfix: String = ""
    ) : super(left, operator, right, prefix, postfix)

    override val sql: String by laziest {
        "`${left.name}` $operator ${left parseToSql right}"
    }

    override val boundSql: String by laziest {
        "`${left.name}` $operator ?"
    }

    override val columnValues: List<*> by laziest {
        listOf(
            right
        )
    }

}

