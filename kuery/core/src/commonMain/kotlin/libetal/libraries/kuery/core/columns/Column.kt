package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression

/**
 * A map of properties makes more
 * sense here than to have different
 * classes for each column
 **/
open class Column<T>(
    val name: String,
    open val baseSql: String,
    val primary: Boolean = false,
    val nullable: Boolean = false,
    val parser: (String?) -> T
) {

    var default: T? = null
        protected set

    constructor(name: String, baseSql: String, default: T? = null, parser: (String?) -> T) : this(
        name,
        baseSql,
        false,
        false,
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

    open val T.sqlString
        get() = when (this) {
            is CharSequence -> "'$this'"
            else -> toString()
        }

    /*converts this instance to charSequence column type*/
    fun toCharSetColumn(): CharacterSequence<*> {
        TODO("")
    }

    open val createSql: String by laziest {
        baseSql
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

class FinalColumn<T>(
    name: String,
    sql: String,
    primary: Boolean = false,
    nullable: Boolean = false,
    val toSqlString: (T) -> String = { it.toString() },
    parser: (String?) -> T
) : Column<T>(
    name,
    sql,
    primary,
    nullable,
    parser
) {
    override val T.sqlString: String
        get() = toSqlString(this)
}