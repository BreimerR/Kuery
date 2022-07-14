@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier


class Select(
    override val where: String,
    override val boundWhere: String
) : ArgumentsStatement(), WhereStatement {

    var orderBy: Column<*>? = null

    private val orderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
    }

    var limit: Long? = null
        private set

    val limitSql by laziest {
        limit?.let {
            " LIMIT $it"
        } ?: ""
    }

    val boundLimitSql by laziest {
        limit?.let {
            " LIMIT ?"
        } ?: ""
    }

    val columns by laziest {
        mutableListOf<Column<*>>()
    }

    val columnsSql by laziest {
        "`${columns.joinToString("`, `") { it.name }}`"
    }

    val columnValues by laziest {
        mutableListOf<Any>()
    }

    private val boundOrderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
    }

    var groupBy: Column<*>? = null

    var groupBySql by laziest {
        groupBy?.let { " $GROUP_BY $it" } ?: ""
    }

    var boundGroupBySql by laziest {
        groupBy?.let { " $GROUP_BY ?" } ?: ""
    }

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    override val sql by laziest {
        "SELECT $columnsSql FROM ${entity.identifier} WHERE $where$groupBySql$orderBySql$limitSql"
    }

    infix fun LIMIT(limit: Long): Select = this.also {
        it.limit = limit
        it.columnValues.add(limit)
    }

    override val boundSql by laziest {
        "SELECT $columnsSql FROM ${entity.identifier} WHERE $boundWhere$boundGroupBySql$boundOrderBySql$boundLimitSql"
    }

    companion object {
        const val GROUP_BY = "GROUP BY"
        const val ORDER_BY = "ORDER BY"
    }
}


