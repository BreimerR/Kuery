package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identity
import libetal.libraries.kuery.core.entities.extensions.name

class Delete<T, E : Entity<T>>(sql: String, entity: E, val boundWheres: String = "") : Statement<T, E>(sql, entity),
    WhereStatement {

    override val wheres: MutableList<Pair<String, Any>> by laziest {
        mutableListOf()
    }

    override val boundSql: String
        get() = "DELETE FROM ${entity.identity} $boundWheres"

}