@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.AbstractExpression
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.statements.builders.StatementFactory
import libetal.libraries.kuery.core.statements.builders.UpdateStatementBuilder



object UPDATE : StatementFactory<Update<*, *>>()
