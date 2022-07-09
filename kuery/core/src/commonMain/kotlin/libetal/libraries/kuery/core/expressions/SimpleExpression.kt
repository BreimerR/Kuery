@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.statements.Select
import libetal.libraries.kuery.core.statements.Statement

class SimpleExpression<T> : Expression<String, String> {

    constructor(
        left: Column<T>,
        operator: Operators,
        right: T
    ) : this(left.name, operator.toString(), with(left) { right.sqlString })

    private constructor(column: String, operator: String, right: String) : super(column, operator, right)

    constructor(column: String, operator: Operators, value: String) : this(column, operator.toString(), value)

    override val sql: String
        get() = "`$left` $operator $right"

    override val boundSql: String by laziest {
        "`$left` $operator ?"
    }
    override val columnValues: List<Any> by laziest {
        listOf(
            right
        )
    }

}

class StatementExpression<T> : Expression<Column<T>, Select> {

    constructor(
        left: Column<T>,
        operator: Operators,
        right: Select
    ) : super(left, operator, right)

    override val sql: String by laziest {
        "${left.identifier} = ${right.sql}"
    }

    override val boundSql: String by laziest {
        "${left.identifier} = ${right.boundSql}"
    }

    override val columnValues: List<Any> by laziest {
        buildList {
            addAll(
                right.columnValues
            )
        }
    }

}









