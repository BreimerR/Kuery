@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

/**
 * Need to map results to the statement
 * This could have multiple entities.
 **/
abstract class Statement<T, E : Entity<T>> {

    abstract var entity: E

    /**
     * Watch Shaw Shank Nice preview 😅
     **/
    val columns by laziest {
        mutableListOf<Column<T>>()
    }

    protected abstract var sql: String

    infix fun <T, E : Entity<T>, S : Statement<T, E>> S.append(string: String): S = also {
        sql += " $string"
    }

    override fun toString() = sql

}
