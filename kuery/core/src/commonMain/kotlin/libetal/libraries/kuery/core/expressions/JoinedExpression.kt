package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest

class JoinedExpression(
    left: Expression<*, *>,
    operator: Operators,
    right: Expression<*, *>
) : Expression<Expression<*, *>, Expression<*, *>>(left, operator.toString(), right) {

    override val sql: String by laziest {
        "($left $operator $right)"
    }

    override val boundSql: String by laziest {
        "(${left.boundSql} $operator ${right.boundSql}"
    }

    override val columnValues: List<*> by laziest {
        buildList {
            addAll(left.columnValues)
            addAll(right.columnValues)
        }
    }

}