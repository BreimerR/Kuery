package libetal.libraries.kuery.sqlite.core.columns

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

class Real(name: String,  primary: Boolean = false) : Column<Number>(name, "`$name` REAL", primary) {

    override fun Number.defaultSql(): String = this.toString()


}
