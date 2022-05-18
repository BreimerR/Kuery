package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.builders.StatementFactory

object CREATE : StatementFactory<Create<*, *>>() {
    @Suppress("FunctionName")
    fun <Class, E : Entity<Class>> TABLE(entity: E, database: Kuery<*>) = Create(entity, database)
}