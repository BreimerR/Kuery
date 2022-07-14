package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.statements.parseToSql

/**TODO
 * SQL Concat value to prefix and postfix
 **/
class DecoratedExpression<T : Any> : DecoratedExpressions<EntityColumn<T>, T> {

    constructor(
        left: EntityColumn<T>,
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

    override val columnValues: List<*> by laziest {
        listOf(
            right
        )
    }

}

