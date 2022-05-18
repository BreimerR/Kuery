package libetal.libraries.kuery.sqlite.core.columns

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

class Blob(name: String, table: Entity<*, *, *>, primary: Boolean = false) : Column<Any>(name, table, primary) {
    override val createSql: String
        get() = "`$name` BLOB"


    override fun Any.defaultSql(): String = TODO("Not sure if sqlite has defaults yet")
}