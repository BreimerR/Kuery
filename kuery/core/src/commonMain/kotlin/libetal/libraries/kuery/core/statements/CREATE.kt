package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.builders.StatementFactory

object CREATE : StatementFactory<Create<*, *>>() {

    infix fun <Class, E : Entity<Class>> TABLE(entity: E) = Create(entity, Entity.Type.TABLE)

    infix fun <T, E : Entity<T>> VIEW(entity: E) = Create(entity, Entity.Type.VIEW)

}

