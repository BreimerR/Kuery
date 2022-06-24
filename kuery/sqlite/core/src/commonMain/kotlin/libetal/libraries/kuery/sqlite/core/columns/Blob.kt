package libetal.libraries.kuery.sqlite.core.columns

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.Entity

class Blob(name: String, primary: Boolean = false) : Column<Any>(
    name,
    "`$name` BLOB",
    primary,
    { string -> string ?: throw RuntimeException("Received null. Expected string from database") }) {
    override fun Any.defaultSql(): String = TODO("Not sure if sqlite has defaults yet")

}