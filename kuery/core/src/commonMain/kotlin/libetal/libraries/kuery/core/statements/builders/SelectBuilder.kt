@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

class SelectBuilder(override val sql: String, vararg val columns: Column<*>) : StatementBuilder