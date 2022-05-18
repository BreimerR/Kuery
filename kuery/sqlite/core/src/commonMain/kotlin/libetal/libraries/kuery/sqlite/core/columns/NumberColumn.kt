package libetal.libraries.kuery.sqlite.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity

class NumberColumn<N : Number>(
    name: String,
    table: Entity<*>,
    size: N? = null,
    primary: Boolean = false
) : libetal.libraries.kuery.core.columns.NumberColumn<N>(
    name,
    table,
    size,
    primary
) {

    override val unSizedSql: String
        get() = "`$name` NUMBER"

    override val createSql: String by laziest {
        super.createSql + defaultSql
    }

    override fun N.defaultSql(): String = this.toString()

}