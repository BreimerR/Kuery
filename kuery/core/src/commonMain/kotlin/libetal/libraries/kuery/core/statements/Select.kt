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

    val columns by laziest {
        mutableListOf<Column<*>>()
    }

    val columnsSql by laziest {
        "`${columns.joinToString("`, `") { it.name }}`"
    }

    val columnValues by laziest{
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
        "SELECT $columnsSql FROM ${entity.identifier} $where$groupBySql$orderBySql"
    }

    override val boundSql by laziest {
        "SELECT $columnsSql FROM ${entity.identifier} $boundWhere$boundGroupBySql$boundOrderBySql"
    }

    companion object {
        const val GROUP_BY = "GROUP BY"
        const val ORDER_BY = "ORDER BY"
    }
}


