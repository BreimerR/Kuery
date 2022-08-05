package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.expressions.Expression.Operators.EQUALS
import libetal.libraries.kuery.core.expressions.SimpleExpression

abstract class BaseColumn<T>(
    override val name: String,
    sql: String,
    protected val default: T? = null,
    override val nullable: Boolean = false,
    protected val toSqlString: (T) -> String = { it.toString() },
    override val alias: String? = null,
    override val parser: (String?) -> T
) : Column<T> {

    /**
     * Most databases support
     * `columnName` this will be preferred until a constraint is met
     * Proposed solution
     * 1. abstract the property
     *     > Would warrant need for each database system to implement this class
     *     making common columns unusable
     *     But if there are no common columns then having this might not be such a
     *     bad Idea
     * But some also support
     * `tableName.columnName`
     **/
    override val identifier by laziest {
        "$name"
    }

    override val sql by laziest {
        "$sql$defaultSql$nullableSql"
    }

    val T.sqlString: String
        get() = toSqlString(this)

    protected val defaultSql by laziest {
        default?.let(toSqlString)?.let { " DEFAULT $it" } ?: ""
    }

    protected val nullableSql by laziest {
        if (nullable) "" else " NOT NULL"
    }

    val T.equalsExpression
        get() = SimpleExpression(this@BaseColumn, EQUALS, this)

}
