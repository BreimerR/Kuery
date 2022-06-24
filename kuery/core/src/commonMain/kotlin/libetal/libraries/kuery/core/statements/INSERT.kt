@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.statements.builders.StatementFactory

object INSERT : StatementFactory<Insert<*, *>>()
