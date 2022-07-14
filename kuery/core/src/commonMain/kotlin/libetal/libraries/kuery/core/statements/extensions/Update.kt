@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.statements.UPDATE
import libetal.libraries.kuery.core.statements.Update
import libetal.libraries.kuery.core.statements.builders.UpdateStatementBuilder

infix fun <T, E : Entity<T>> UPDATE.TABLE(entity: E) = UpdateStatementBuilder(entity)

infix fun <T, E : Entity<T>> UpdateStatementBuilder<T, E>.SET(expressionBuilder: UpdateStatementBuilder<T, E>.(E) -> Unit) =
    also {
        expressionBuilder(entity)
    }


