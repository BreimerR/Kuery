package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Select

class SelectStatementBuilder<T, E : Entity<T>>(val entity: E, vararg val columns: Column<*>) :
    WhereStatementBuilder<T, E, Select>() {
    override fun build(where: String, boundWhere: String) = Select(entity, where, boundWhere, columnValues, *columns)

}





