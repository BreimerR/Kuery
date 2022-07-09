@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Select

infix fun <C> Select.ORDER_BY(column: Column<C>) = this.apply {
    orderBy = column
}
