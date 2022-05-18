@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Select

infix fun <C, T, E : Entity<T>> Select<T, E>.ORDER_BY(column: Column<C>) =
    append("ORDER_BY `${column.identifier}`")