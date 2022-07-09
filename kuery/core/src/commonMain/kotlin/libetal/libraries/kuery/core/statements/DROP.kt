package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.builders.StatementFactory

object DROP : StatementFactory<Drop>() {
    @Suppress("FunctionName")
    infix fun <Class, E : Entity<Class>> TABLE(entity: E) = Drop(entity)
}