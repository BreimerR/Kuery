@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier

class Select<T, E : Entity<T>>(sql: String, entity: E) : Statement<T, E>(sql, entity), WhereStatement {

    var limit: Long? = null

    var orderBy: Column<*>? = null

    var groupBy: Column<*>? = null

    override val wheres: MutableList<Pair<String, Any>> by laziest {
        mutableListOf()
    }
    override val boundSql: String
        get() = TODO("Not yet implemented")

}


class FinalSelect(
    override val where: String,
    override val boundWhere: String
) : FinalStatement(), FinalWhereStatement {

    var orderBy: Column<*>? = null

    private val orderBySql by laziest {
        orderBy?.let {
            " $ORDER_BY $it"
        } ?: ""
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

    private val columnsSql by laziest {
        "`${columns.joinToString("`,`") { it.name }}`"
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


