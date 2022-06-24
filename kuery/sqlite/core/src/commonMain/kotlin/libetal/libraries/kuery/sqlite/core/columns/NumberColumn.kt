package libetal.libraries.kuery.sqlite.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity

class NumberColumn<N : Number>(
    name: String,
    size: N? = null,
    primary: Boolean = false,
    parser: (String?) -> N
) : libetal.libraries.kuery.core.columns.NumberColumn<N>(
    name,
    "`$name` NUMBER",
    size,
    primary,
    false,
    parser
) {

    override val createSql: String by laziest {
        defaultSql + defaultSql
    }

    override fun N.defaultSql(): String = this.toString()

}