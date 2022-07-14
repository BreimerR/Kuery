package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.expressions.Expression.Operators.EQUALS
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.statements.parseToSql

class EntityColumn<T>(
    override val name: String,
    val sql: String,
    val primary: Boolean = false,
    override val nullable: Boolean = false,
    val toSqlString: (T) -> String = { it.toString() },
    override val parser: (String?) -> T
) : Column<T> {

    val type = ""

    var default: T? = null
        private set

    val T.equalsExpression
        get() = SimpleExpression(this@EntityColumn, EQUALS, this)

    val defaultKeyWord: String = "DEFAULT"

    val defaultSql by laziest {
        if (primary) "" else (default?.let { " $defaultKeyWord ${this parseToSql it}" } ?: "")
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
    override val identifier by laziest {
        "`$name`"
    }

    val T.sqlString: String
        get() = toSqlString(this)

    val createSql: String by laziest {
        sql
    }

    private val nullableSql by laziest {
        if (nullable) "" else " NOT NULL"
    }

    private val primarySql by laziest {
        if (primary) "" else " PRIMARY KEY"
    }

    val buildSql by laziest {
        "$identifier $type$primarySql$defaultSql$nullableSql"
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

}

