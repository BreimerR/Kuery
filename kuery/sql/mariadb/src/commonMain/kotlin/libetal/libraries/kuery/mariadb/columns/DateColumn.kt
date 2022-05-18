package libetal.libraries.kuery.mariadb.columns

import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.SizedColumn
import libetal.libraries.kuery.mariadb.entities.Entity

/**
 * Using LocalDate Makes more sense
 * LocalDate does not utilize the zone of use
 * and does the database utilize that or a ZoneBased storage
 * not sure how the behaviour would look like
 **/
class DateColumn : Column<LocalDate> {

    var format: String? = null
        private set

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
        default: LocalDate? = null,
        format: String? = null
    ) : super(
        name,
        table,
        default
    ) {
        this.format = format
    }

    override val createSql: String by laziest {
        "`$name` DATE$defaultSql"
    }

    override fun LocalDate.defaultSql(): String =
        " DEFAULT STR_TO_DATE('$this','${format ?: throw RuntimeException("Can't set a date without a format")}')"
}