package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Delete

class DeleteStatementBuilder<T, E : Entity<T>>(extras: String, entity: E) :
    EntityStatementBuilder<T, E, Delete<T, E>>("DELETE $extras", entity = entity) {
    override fun build(extras: String): Delete<T, E> = Delete(sql)

}



