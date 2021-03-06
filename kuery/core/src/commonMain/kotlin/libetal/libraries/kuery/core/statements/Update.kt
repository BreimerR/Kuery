package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier
import libetal.libraries.kuery.core.statements.results.UpdateResult

class Update(
    override val where: String,
    override val boundWhere: String
) : ArgumentsStatement<UpdateResult>(), WhereStatement {

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    val set by laziest {
        mutableListOf<Pair<EntityColumn<*>, *>>()
    }

    val boundSet by laziest {
        mutableListOf<Pair<EntityColumn<*>, String>>()
    }

    val setSql by laziest {
        set.joinToString(", ") { (column, value) ->
            "${column.identifier} = ${column parseToSql value}"
        }
    }

    val boundSetSql by laziest {
        boundSet.joinToString(", ") { (column, value) ->
            "${column.identifier} = $value"
        }
    }

    val prefixSql by laziest {
        "UPDATE ${entity.identifier} SET"
    }

    override val sql by laziest {
        "$prefixSql $setSql WHERE $where"
    }

    override val boundSql by laziest {
        "$prefixSql $boundSetSql WHERE $boundWhere"
    }

}