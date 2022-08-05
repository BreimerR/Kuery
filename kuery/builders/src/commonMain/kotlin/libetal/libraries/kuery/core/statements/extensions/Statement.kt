@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.WhereScope
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.builders.EntityStatementBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder
import libetal.libraries.kuery.core.statements.builders.WhereStatementBuilder

/**
 * This design might lead
 * to unwanted SQL generation
 * unless a default operator is utilized i.e. AND / OR
 * Suggestions OR: This is because or is will  always return a result compared to AND
 **/