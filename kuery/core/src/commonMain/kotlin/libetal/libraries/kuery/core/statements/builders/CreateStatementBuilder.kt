package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.Create

class CreateStatementBuilder<T, E : Entity<T>>(entity: E) :
    EntityStatementBuilder<T, E, Create<T, E>>("CREATE `${entity.name}`", entity = entity) {
    override fun build(extras: String): Create<T, E> = TODO("Implementation redundant for create")
}