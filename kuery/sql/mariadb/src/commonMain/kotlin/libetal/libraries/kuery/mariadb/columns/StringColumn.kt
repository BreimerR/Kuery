package libetal.libraries.kuery.mariadb.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.SizedColumn
import libetal.libraries.kuery.mariadb.entities.Entity

/**
 * Using LocalDate Makes more sense
 * LocalDate does not utilize the zone of use
 * and does the database utilize that or a ZoneBased storage
 * not sure how the behaviour would look like
 **/
class StringColumn : SizedColumn<String, Int> {

    constructor(
        name: String,
        table: Entity<*>,
        size: Int = 50,
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
        size: Int = 50,
        default: String? = null,
    ) : super(
        name,
        table,
        default,
        size
    )

    override val unSizedSql: String
        get() = "`$name` VARCHAR"

    override fun String.defaultSql(): String = "'$this'"

    override val createSql: String by laziest {
        super.createSql + primarySql + defaultSql
    }

}