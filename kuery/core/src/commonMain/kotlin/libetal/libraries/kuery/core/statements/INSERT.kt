@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.builders.InsertStatementBuilder
import libetal.libraries.kuery.core.statements.builders.StatementFactory

object INSERT : StatementFactory<Insert<*, *>>()
