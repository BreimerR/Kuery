@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.statements.builders.StatementFactory

class CreateStatementFactory(val database: Kuery<*>) : StatementFactory<Create<*, *>>() {

    infix fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> VIEW(entity: E) =
        Create(entity, database, libetal.libraries.kuery.core.entities.Entity.Type.VIEW)

}


