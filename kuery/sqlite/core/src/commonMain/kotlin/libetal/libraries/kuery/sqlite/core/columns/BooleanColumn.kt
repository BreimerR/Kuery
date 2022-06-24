package libetal.libraries.kuery.sqlite.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

/**
 * SQLite doesn't support booleans
 * thus need to store this value as an integer and
 * retrieve as int and convert to boolean
 **/
class BooleanColumn(name: String, primary: Boolean = false) : Column<Boolean>(name, "`$name` NUMBER", primary, ::toBoolean) {

    override val Boolean.sqlString
        get() = if (this) "1" else "0"

    override fun Boolean.defaultSql(): String = sqlString
}

fun toBoolean(string: String?) =
    string?.toBoolean() ?: throw NullPointerException("Received null value from db for a non null field")
