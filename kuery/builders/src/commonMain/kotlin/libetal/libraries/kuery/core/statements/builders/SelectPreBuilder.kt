@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.Column

class SelectPreBuilder(vararg val columns: Column<*>)