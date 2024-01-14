package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.kotlin.log.info
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier
import libetal.libraries.kuery.core.statements.results.UpdateResult

class Update(
    override val where: String,
    override val boundWhere: String,
    val set: MutableList<Pair<BaseColumn<*>, *>>,
    vararg val columnValues: Any
) : ArgumentsStatement<UpdateResult>(), WhereStatement {

    init {
        for ((col, value) in set) {
            TAG info "Setting ${col.name} = ?"
            arguments.add(value)
        }
        // Adds where arguments
        for (value in columnValues) {
            arguments.add(value)
        }
    }

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    private val setSql by laziest {
        set.joinToString(", ") { (column, value) ->
            "${column.identifier} = ${column parseToSql value}"
        }
    }

    private val boundSetSql by laziest {
        set.joinToString(", ") { (column, value) ->
            "${column.identifier} = ?"
        }
    }

    private val prefixSql by laziest {
        "UPDATE ${entity.identifier} SET"
    }

    override val sql by laziest {
        "$prefixSql $setSql WHERE $where"
    }

    override val boundSql by laziest {
        "$prefixSql $boundSetSql WHERE $boundWhere"
    }


    companion object {
        const val TAG = "Update"
    }
}
