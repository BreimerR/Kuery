package libetal.libraries.kuery.mariadb.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.mariadb.entities.Entity

class CharColumn : Column<Char> {

    constructor(
        name: String,
        table: Entity<*>
    ) : super(
        name,
        table,
        false
    )

    constructor(
        name: String,
        table: Entity<*>,
        default: Char? = null,
    ) : super(
        name,
        table,
        default,
    )


    override val createSql: String by laziest {
        "`$name` CHAR" + (default?.let { " DEFAULT '$it'" } ?: "")
    }

    override fun Char.defaultSql(): String = "'$this'"

}

