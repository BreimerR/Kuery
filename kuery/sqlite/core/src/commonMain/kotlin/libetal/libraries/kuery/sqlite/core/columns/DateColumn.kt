package libetal.libraries.kuery.sqlite.core.columns

import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

/*
class DateColumn : Column<LocalDate> {

    var format: String? = null
        private set

    constructor(
        name: String,
        primary: Boolean = false
    ) : super(
        name,
        primary
    )

    */
/**
     * DEFAULT VALUE SUPPORT IS DIFFERENT HERE AS IT CAN CONTAIN
     * BUT THIS DEPENDS ON IF WE WANT TO CALCULATE IT FROM THE DATABASE ON THE RUNNING SERVER
     * IMPLEMENTATION.
     *
     * 1. ACTUAL DATE
     * 2. FUNCTION CALL TO I.E DATE('now')
     *      1. DATE(time-value, modifier, modifier, ...)
     *      2. TIME(time-value, modifier, modifier, ...)
     *      3. DATETIME(time-value, modifier, modifier, ...)
     *      4. JULIANDAY(time-value, modifier, modifier, ...)
     *      5. UNIXEPOCH(time-value, modifier, modifier, ...)
     *      6. STRFTIME(format, time-value, modifier, modifier, ...)
     *   > Where time-value can be [```now``` | ```most date formats```]
     **//*

    constructor(
        name: String,
        default: LocalDate? = null,
        format: String? = null
    ) : super(
        name,
        default
    ) {
        if (default == null && format != null) throw RuntimeException("Can't set format if default date isn't provided")
        this.format = format
    }

    override val baseSql: String
        get() = "`$name` TEXT"

    override val createSql: String by laziest {
        // DATETIME DEFAULT (strftime('%Y-%m-%dT%H:%M:%fZ', 'now'))
        baseSql + defaultSql + nullableSql
    }

    override val defaultKeyWord: String by laziest {
        "DATETIME " + super.defaultKeyWord
    }

    override fun LocalDate.defaultSql(): String = toString() // Default value could be a lambda that returns LocalDate

}*/
