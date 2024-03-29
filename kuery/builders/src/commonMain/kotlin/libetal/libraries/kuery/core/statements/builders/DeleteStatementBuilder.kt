package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Delete

class DeleteStatementBuilder<T, E : Entity<T>>(val entity: E) : WhereStatementBuilder<T, E, Delete>() {
    override fun build(where: String, boundWhere: String, vararg columnValues: Any) = Delete(entity, where, boundWhere, *columnValues)

}



