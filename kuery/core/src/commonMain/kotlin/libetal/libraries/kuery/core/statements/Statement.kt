@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column

/**
 * Need to map results to the statement
 * This could have multiple entities.
 **/
abstract class Statement {

    abstract val sql: String

    abstract val boundSql: String

    override fun toString() = sql

}

abstract class ArgumentsStatement : Statement() {
    val arguments by laziest {
        mutableListOf<Any>()
    }
}

infix fun <T> Column<*>.parseToSql(obj: T) = try {
    @Suppress("UNCHECKED_CAST")
    this as Column<T>
    obj.sqlString
} catch (e: Exception) {
    obj.toString()
}



