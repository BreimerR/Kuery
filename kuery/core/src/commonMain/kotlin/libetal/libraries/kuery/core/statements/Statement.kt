@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.statements.results.Result

/**
 * Need to map results to the statement
 * This could have multiple entities.
 **/
abstract class Statement<R : Result> {

    abstract val sql: String

    abstract val boundSql: String

    override fun toString() = sql

}

abstract class ArgumentsStatement<T : Result> : Statement<T>() {
    val arguments by laziest {
        mutableListOf<Any>()
    }
}

/**
 * If it's a wrong T then it should fail
 **/
infix fun <T> EntityColumn<*>.parseToSql(obj: T) = try {
    @Suppress("UNCHECKED_CAST")
    this as EntityColumn<T>
    obj.sqlString
} catch (e: Exception) {
    obj.toString()
}

fun <T> EntityColumn<*>.parseToSqlError(obj: T, message: String): String {
    val column = try {
        @Suppress("UNCHECKED_CAST")
        this as EntityColumn<T>
    } catch (e: ClassCastException) {
        throw RuntimeException("$message: ${e.message}")
    }

    return with(column) {
        try {
            obj.sqlString
        } catch (e: ClassCastException) {
            throw RuntimeException("$column, $message: ${e.message}")
        }
    }
}



