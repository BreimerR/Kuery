@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

/**
 * Need to map results to the statement
 * This could have multiple entities.
 **/
abstract class Statement<T, E : Entity<T>>(val sql: String, val entity: E) {

    abstract val boundSql: String

    /**
     * Watch Shaw Shank Nice preview 😅
     **/
    val columns by laziest {
        mutableListOf<Column<*>>()
    }

    override fun toString() = sql

}


abstract class FinalStatement {

    abstract val sql: String

    val columns by laziest {
        mutableListOf<Column<*>>()
    }

}



