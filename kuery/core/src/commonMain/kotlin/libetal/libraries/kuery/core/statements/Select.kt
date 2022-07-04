@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

class Select<T, E : Entity<T>>(sql: String, entity: E) : Statement<T, E>(sql, entity), WhereStatement {

    var limit: Long? = null

    var orderBy: Column<*>? = null

    val groupBy: Column<*>? = null

    override val wheres: MutableMap<Column<*>, Any> by laziest {
        mutableMapOf()
    }

}



