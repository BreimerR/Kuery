package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.expressions.Expression.Operators.EQUALS
import libetal.libraries.kuery.core.expressions.SimpleExpression

class Column<T>(
    val name: String,
    val sql: String,
    val primary: Boolean = false,
    val nullable: Boolean = false,
    val toSqlString: (T) -> String = { it.toString() },
    val parser: (String?) -> T
) {

    var default: T? = null
        private set

    val T.equalsExpression
        get() = SimpleExpression(this@Column, EQUALS, this)

    val defaultKeyWord: String = "DEFAULT"

    val defaultSql by laziest {
        if (primary) "" else (default?.let { " $defaultKeyWord ${it.defaultSql()}" } ?: "")
    }

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
    val identifier by laziest {
        "`$name`"
    }

    val T.sqlString: String
        get() = toSqlString(this)

    val createSql: String by laziest {
        sql
    }

    val nullableSql by laziest {
        if (nullable) "" else " NOT NULL"
    }

    constructor(
        name: String,
        baseSql: String,
        default: T? = null,
        toSqlString: (T) -> String = { it.toString() },
        parser: (String?) -> T
    ) : this(
        name,
        baseSql,
        false,
        false,
        toSqlString,
        parser
    ) {
        this.default = default
    }


    // Can be a passed argument with default converter lambda
    fun T.defaultSql(): String = throw RuntimeException("Implementation pending: Column.kt 58")

    // TODO make abstract once all are correctly implementing
    fun parse(value: String?): T = parser(value)

}

