package libetal.libraries.kuery.core.expressions

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