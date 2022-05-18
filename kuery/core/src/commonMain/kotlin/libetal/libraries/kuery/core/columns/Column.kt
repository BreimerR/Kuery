package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression

abstract class Column<T>(
    val name: String,
    table: Entity<*>,
    val primary: Boolean = false,
) {

    var default: T? = null
        protected set

    constructor(name: String, table: Entity<*>, default: T? = null) : this(name, table, false) {
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

    abstract val createSql: String

    abstract fun T.defaultSql(): String

    open val defaultKeyWord: String = "DEFAULT"

    val defaultSql by laziest {
        (default?.let { " $defaultKeyWord ${it.defaultSql()}" } ?: "")
    }

    val primarySql by laziest {
        if (primary) " PRIMARY KEY" else ""
    }

    override fun toString(): String = identifier

    val T.equalsExpression
        get() = Expression(identifier, "==", sqlString)

}

