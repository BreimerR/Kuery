@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.WhereScope
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.builders.EntityStatementBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder

/**
 * This design might lead
 * to unwanted SQL generation
 * unless a default operator is utilized i.e. AND / OR
 * Suggestions OR: This is because or is will  always return a result compared to AND
 **/
infix fun <T, E : Entity<T>, S : Statement<T, E>> EntityStatementBuilder<T, E, S>.WHERE(expressionBuilder: WhereScope.() -> Expression<*>): S =
    WHERE(expressionBuilder(WhereScope()))

infix fun <T, E : Entity<T>, S : Statement<T, E>> EntityStatementBuilder<T, E, S>.WHERE(expression: Expression<*>): S =
    entity.buildWhere(expression.sql.let {
        if (it[0] == '(') it.substring(1, it.length - 1) else it
    },expression.boundSql).also {

        when (it) {

            is Select<*, *> -> {
                it.wheres += expression.columnValues
            }

            is Delete<*, *> -> {
                it.wheres += expression.columnValues
            }

            is Update<*,*> -> {
                it.wheres += expression.columnValues
            }




        }
    }

infix fun <T, E : Entity<T>, S : Statement<T, E>> EntityStatementBuilder<T, E, S>.WHERE(boolean: Boolean): S =
    entity.buildWhere("$boolean")