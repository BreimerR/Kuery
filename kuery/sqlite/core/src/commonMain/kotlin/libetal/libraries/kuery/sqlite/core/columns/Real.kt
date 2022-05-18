package libetal.libraries.kuery.sqlite.core.columns

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

class Real(name: String, table: Entity<*, *, *>, primary: Boolean = false) : Column<Number>(name, table, primary) {

    override val createSql: String
        get() = "`$name` REAL"

    override fun Number.defaultSql(): String = this.toString()


}
