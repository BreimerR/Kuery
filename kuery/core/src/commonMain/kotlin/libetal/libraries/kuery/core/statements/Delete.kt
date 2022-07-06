package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier

class Delete<T, E : Entity<T>>(sql: String, entity: E, val boundWheres: String = "") : Statement<T, E>(sql, entity),
    WhereStatement {

    override val wheres: MutableList<Pair<String, Any>> by laziest {
        mutableListOf()
    }

    override val boundSql: String
        get() = "DELETE FROM ${entity.identifier} $boundWheres"

}


class FinalDelete(
    override val where: String,
    override val boundWhere: String
) : FinalStatement(), FinalWhereStatement {

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    override val sql by laziest {
        "DELETE FROM ${entity.identifier} $where"
    }

    override val boundSql by laziest {
        "DELETE FROM ${entity.identifier} $boundWhere"
    }
}