@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.WhereScope
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.core.statements.builders.EntityStatementBuilder

/**
 * This design might lead
 * to unwanted SQL generation
 * unless a default operator is utilized i.e. AND / OR
 * Suggestions OR: This is because or is will  always return a result compared to AND
 **/
infix fun <T, E : Entity<T>, S : Statement<T, E>> EntityStatementBuilder<T, E, S>.WHERE(expressionBuilder: WhereScope.() -> Expression): S =
    WHERE(expressionBuilder(WhereScope()))

infix fun <T, E : Entity<T>, S : Statement<T, E>> EntityStatementBuilder<T, E, S>.WHERE(expression: Expression): S =
    entity.buildFrom(expression.sql)

infix fun <T, E : Entity<T>, S : Statement<T, E>> EntityStatementBuilder<T, E, S>.WHERE(boolean: Boolean): S =
    entity.buildFrom("$boolean")