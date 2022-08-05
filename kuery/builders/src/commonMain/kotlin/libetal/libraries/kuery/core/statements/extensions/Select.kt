@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.statements.Select

infix fun <C> Select.ORDER_BY(column: GenericColumn<C>) = this.apply {
    orderBy = column
}
