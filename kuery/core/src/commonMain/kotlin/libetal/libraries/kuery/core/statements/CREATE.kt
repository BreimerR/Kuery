package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.TableEntity
import libetal.libraries.kuery.core.entities.ViewEntity
import libetal.libraries.kuery.core.statements.builders.StatementFactory

object CREATE : StatementFactory<Create<*, *>>() {

    infix fun <Class, E : TableEntity<Class>> TABLE(entity: E) = Create(entity, Entity.Type.TABLE)

    infix fun <T, E : ViewEntity<T>> VIEW(entity: E) = Create(entity, Entity.Type.VIEW)

}