package libetal.libraries.kuery.sqlite.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

/**
 * SQLite doesn't support booleans
 * thus need to store this value as an integer and
 * retrieve as int and convert to boolean
 **/
class BooleanColumn(name: String, table: Entity<*, *, *>, primary: Boolean = false) : Column<Boolean>(name, table, primary) {

    override val Boolean.sqlString
        get() = if (this) "1" else "0"

    override val createSql: String by laziest {
        "`$name` NUMBER$defaultSql"
    }

    override fun Boolean.defaultSql(): String = sqlString
}