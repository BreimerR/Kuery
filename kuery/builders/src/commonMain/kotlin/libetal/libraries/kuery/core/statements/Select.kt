@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier
import libetal.libraries.kuery.core.statements.results.SelectResult


class Select(
    val entity: Entity<*>,
    override val where: String,
    override val boundWhere: String,
    columnValues: Array<out Any>,
    vararg val columns: Column<*>
) : ArgumentsStatement<SelectResult>(), WhereStatement {

    var orderBy: GenericColumn<*>? = null

    private val orderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
    }

    private var limit: Long? = null

    private val limitSql by laziest {
        limit?.let {
            " LIMIT $it"
        } ?: ""
    }

    private val boundLimitSql by laziest {
        limit?.let {
            " LIMIT ?"
        } ?: ""
    }

    private val columnsSql by laziest {
        columns.joinToString(", ") { it.identifier }
    }

    private val boundOrderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
    }

    private var groupBy: GenericColumn<*>? = null

    private var groupBySql by laziest {
        groupBy?.let { " $GROUP_BY $it" } ?: ""
    }

    private var boundGroupBySql by laziest {
        groupBy?.let { " $GROUP_BY ?" } ?: ""
    }

    private val bareSql by laziest {
        "SELECT $columnsSql FROM ${entity.identifier} WHERE"
    }

    override val sql by laziest {
        "$bareSql $where$groupBySql$orderBySql$limitSql"
    }

    init {
        for (value in columnValues) {
            arguments.add(value)
        }
    }

    infix fun LIMIT(limit: Long): Select = this.also {
        it.limit = limit
        it.arguments.add(limit)
    }

    override val boundSql by laziest {
        "$bareSql $boundWhere$boundGroupBySql$boundOrderBySql$boundLimitSql"
    }

    companion object {
        const val GROUP_BY = "GROUP BY"
        const val ORDER_BY = "ORDER BY"
    }

}
