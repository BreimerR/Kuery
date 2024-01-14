package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier
import libetal.libraries.kuery.core.statements.results.DeleteResult

class Delete(
    val entity: Entity<*>,
    override val where: String,
    override val boundWhere: String,
    vararg val columnValues: Any
) : ArgumentsStatement<DeleteResult>(), WhereStatement {

    init {
        for (value in columnValues) {
            arguments.add(value)
        }
    }

    private val bareSql: String by laziest {
        "DELETE FROM ${entity.identifier} WHERE"
    }

    override val sql by laziest {
        "$bareSql $where"
    }

    override val boundSql by laziest {
        "$bareSql $boundWhere"
    }

}
