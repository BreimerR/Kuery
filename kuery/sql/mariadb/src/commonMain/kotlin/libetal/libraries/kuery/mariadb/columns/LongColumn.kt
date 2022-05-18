package libetal.libraries.kuery.mariadb.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.NumberColumn
import libetal.libraries.kuery.mariadb.entities.Entity

class LongColumn(
    name: String,
    table: Entity<*>,
    size: Long = 50,
    primary: Boolean = false
) : NumberColumn<Long>(name, table, size, primary) {

    override val unSizedSql: String
        get() = "`$name` LONG"

    override val createSql: String by laziest {
        super.createSql + primarySql
    }

    override fun Long.defaultSql(): String = this.toString()

}