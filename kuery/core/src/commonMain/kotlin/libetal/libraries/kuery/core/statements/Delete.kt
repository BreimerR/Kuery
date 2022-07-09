package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier

class Delete(
    override val where: String,
    override val boundWhere: String
) : ArgumentsStatement(), WhereStatement {

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    val columnValues by laziest {
        mutableListOf<Any>()
    }

    override val sql by laziest {
        "DELETE FROM ${entity.identifier} $where"
    }

    override val boundSql by laziest {
        "DELETE FROM ${entity.identifier} $boundWhere"
    }
}