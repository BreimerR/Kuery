@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.statements.builders.StatementFactory

class CreateStatementFactory(val database: Kuery<*>) : StatementFactory<Create<*, *>>() {



}


