package libetal.libraries.kuery.mariadb.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.NumberColumn
import libetal.libraries.kuery.mariadb.entities.Entity

class FloatColumn : NumberColumn<Float> {

    constructor(
        name: String,
        table: Entity<*>,
        size: Float? = null,
        primary: Boolean = false
    ) : super(
        name,
        table,
        size,
        primary
    )

    constructor(
        name: String,
        table: Entity<*>,
        default: Float? = null,
        size: Float? = null
    ) : super(
        name,
        table,
        default,
        size,
    )

    override val unSizedSql: String
        get() = "`$name` FLOAT"

    override val createSql: String by laziest {
        super.createSql + primarySql + defaultSql
    }

    override fun Float.defaultSql(): String = this.toString()

}