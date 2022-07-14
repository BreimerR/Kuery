@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.statements.Select

infix fun <C> Select.ORDER_BY(column: EntityColumn<C>) = this.apply {
    orderBy = column
}
