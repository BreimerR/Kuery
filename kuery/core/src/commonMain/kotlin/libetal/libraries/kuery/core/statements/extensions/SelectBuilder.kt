@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.builders.SelectBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder

infix fun <T, E : Entity<T>> SelectBuilder.FROM(entity: E) =
    SelectStatementBuilder(sql, entity)