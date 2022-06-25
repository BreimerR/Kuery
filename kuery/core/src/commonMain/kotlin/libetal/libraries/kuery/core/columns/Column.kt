package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.expressions.Expression

/**
 * A map of properties makes more
 * sense here than to have different
 * classes for each column
 **/
/*open class Column<T>(
    val name: String,
    open val baseSql: String,
    val primary: Boolean = false,
    val nullable: Boolean = false,
    val parser: (String?) -> T
)*/

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
    val identifier by laziest { // IN CASE TESTS FAIL CHECK HERE
        // TODO check if it's really worth using "${table.getEntityTableName()}.$name"
        name
    }


    val T.sqlString: String
        get() = toSqlString(this)

    val createSql: String by laziest {
        sql
    }

    val nullableSql by laziest {
        if (nullable) "" else " NOT NULL"
    }


    // Can be a passed argument with default converter lambda
    open fun T.defaultSql(): String = throw RuntimeException("Implementation pending: Column.kt 58")

    open val defaultKeyWord: String = "DEFAULT"

    val defaultSql by laziest {
        if (primary) "" else (default?.let { " $defaultKeyWord ${it.defaultSql()}" } ?: "")
    }

    val primarySql by laziest {
        if (primary) " PRIMARY KEY" else ""
    }

    // TODO make abstract once all are correctly implementing
    open fun parse(value: String?): T = parser(value)

    override fun toString(): String = identifier

    val T.equalsExpression
        get() = Expression(identifier, "==", sqlString)

}

