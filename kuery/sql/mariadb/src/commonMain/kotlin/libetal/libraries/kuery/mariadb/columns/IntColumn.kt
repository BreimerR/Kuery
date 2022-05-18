package libetal.libraries.kuery.mariadb.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.NumberColumn
import libetal.libraries.kuery.mariadb.entities.Entity

class IntColumn : NumberColumn<Int> {

    constructor(
        name: String,
        table: Entity<*>,
        size: Int? = null,
        primary: Boolean = false
    ) : super(name, table, size, primary)

    constructor(
        name: String,
        table: Entity<*>,
        size: Int? = null,
        default: Int? = null
    ) : super(name, table, default, size)

    override val unSizedSql: String by laziest {
        "`$name` INT"
    }

    override fun Int.defaultSql(): String = this.toString()

    override val createSql: String by laziest {
        super.createSql + primarySql
    }

}

