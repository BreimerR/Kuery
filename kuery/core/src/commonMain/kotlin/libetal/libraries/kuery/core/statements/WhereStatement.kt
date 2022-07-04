package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

interface WhereStatement {
    val wheres: MutableMap<Column<*>, Any>
}