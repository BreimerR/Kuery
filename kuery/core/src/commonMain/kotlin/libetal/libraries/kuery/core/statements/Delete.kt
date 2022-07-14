package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier

class Delete(
    val entity: Entity<*>,
    override val where: String,
    override val boundWhere: String,
    vararg val columnValues: Any
) : ArgumentsStatement(), WhereStatement {

    override val sql by laziest {
        "DELETE FROM ${entity.identifier} WHERE $where"
    }

    override val boundSql by laziest {
        "DELETE FROM ${entity.identifier} WHERE $boundWhere"
    }
}