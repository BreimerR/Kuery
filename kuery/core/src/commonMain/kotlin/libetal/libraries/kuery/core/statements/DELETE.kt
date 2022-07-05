@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.builders.DeleteStatementBuilder
import libetal.libraries.kuery.core.statements.builders.StatementFactory

object DELETE : StatementFactory<Delete<*, *>>() {
    infix operator fun <T, E : Entity<T>> times(entity: E) =
        DeleteStatementBuilder("FROM `${entity.name}`", entity)

}

