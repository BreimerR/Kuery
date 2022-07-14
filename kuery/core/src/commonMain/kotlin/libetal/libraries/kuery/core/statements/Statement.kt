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

/**
 * If it's a wrong T then it should fail
 **/
infix fun <T> Column<*>.parseToSql(obj: T) = try {
    @Suppress("UNCHECKED_CAST")
    this as Column<T>
    obj.sqlString
} catch (e: Exception) {
    obj.toString()
}

fun <T> Column<*>.parseToSqlError(obj: T, message: String): String {
    val column = try {
        @Suppress("UNCHECKED_CAST")
        this as Column<T>
    } catch (e: Exception) {
        throw RuntimeException("$message: ${e.message}")
    }

    return with(column) {
        try {
            obj.sqlString
        }catch (e: Exception){
            throw RuntimeException("$column, $message: ${e.message}")
        }
    }
}



