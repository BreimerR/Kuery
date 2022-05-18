@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.statements.DELETE
import libetal.libraries.kuery.core.statements.Delete

infix fun <T, E : Entity<T>> DELETE.FROM(entity: E) =
    times(entity)

infix fun <T, E : Entity<T>> DELETE.ALL(entity: E) =
    times(entity)

infix fun <T, E : Entity<T>> Delete<T, E>.WHERE(expression: Expression) = apply {
    append("WHERE ${expression.sql}")
}

