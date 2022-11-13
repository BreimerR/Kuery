package libetal.libraries.kuery.core.entities

import libetal.kotlin.log.info
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.statements.builders.InsertStatementBuilder
import libetal.libraries.kuery.core.tableEntities

abstract class Entity<T> {

    operator fun invoke(vararg columns: BaseColumn<*>): InsertStatementBuilder<T, Entity<T>> =
        InsertStatementBuilder(this, *columns)

    enum class Type(val identifier: String) {
        TABLE("TABLE"),
        VIEW("VIEW");

        override fun toString(): String = identifier
    }

    abstract override fun toString(): String

    open suspend fun onCreate() {
        TAG info "override this method if you need custom prefilled data for the table"
    }

    companion object {
        const val TAG = "libetal.libraries.kuery.core.entities.Entity"
    }

}


