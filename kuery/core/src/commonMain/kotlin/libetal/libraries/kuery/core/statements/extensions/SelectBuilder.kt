@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.builders.SelectPreBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder

infix fun <T, E : Entity<T>> SelectPreBuilder.FROM(entity: E) =
    SelectStatementBuilder(entity, columns = columns)