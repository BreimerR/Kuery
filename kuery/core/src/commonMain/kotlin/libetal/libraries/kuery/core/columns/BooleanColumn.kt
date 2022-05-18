package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity

class BooleanColumn : Column<Boolean> {

    constructor(
        name: String,
        table: Entity<*>,
        default: Boolean? = null
    ) : super(name, table, default)

    constructor(
        name: String,
        table: Entity<*>
    ) : super(name, table, false)

    override val createSql: String by laziest {
        "`$name` BOOLEAN" + (default?.let { " DEFAULT $it" } ?: "")
    }

    override fun Boolean.defaultSql(): String = this.toString()

}
