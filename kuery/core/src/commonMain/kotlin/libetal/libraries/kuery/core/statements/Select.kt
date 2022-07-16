@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier
import libetal.libraries.kuery.core.statements.results.SelectResult


class Select(
    val entity: Entity<*>,
    override val where: String,
    override val boundWhere: String,
    val columnValues: MutableList<Any>,
    vararg val columns: Column<*>
) : ArgumentsStatement<SelectResult>(), WhereStatement {

    var orderBy: EntityColumn<*>? = null

    private val orderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
    }

    var limit: Long? = null
        private set

    private val limitSql by laziest {
        limit?.let {
            " LIMIT $it"
        } ?: ""
    }

    val boundLimitSql by laziest {
        limit?.let {
            " LIMIT ?"
        } ?: ""
    }

    val columnsSql by laziest {
        var i = 0
        buildString {
            for (column in columns) {
                val identifier = column.identifier
                append(
                    if (i == 0) {
                        identifier
                    } else ", $identifier"
                )
                i++
            }
        }
    }

    private val boundOrderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
    }

    var groupBy: EntityColumn<*>? = null

    var groupBySql by laziest {
        groupBy?.let { " $GROUP_BY $it" } ?: ""
    }

    var boundGroupBySql by laziest {
        groupBy?.let { " $GROUP_BY ?" } ?: ""
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


