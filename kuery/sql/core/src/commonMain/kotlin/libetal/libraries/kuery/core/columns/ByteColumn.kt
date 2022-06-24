package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity

class ByteColumn : Column<Byte> {

    constructor(
        name: String,
    ) : super(name, "`$name` BYTE", false, false, ::toByte)

    constructor(
        name: String,
        table: Entity<*>,
        default: Byte
    ) : super(name, "`$name` BYTE", default, ::toByte)

    override fun Byte.defaultSql(): String = toString()

}

fun toByte(string: String?) =
    string?.toByte() ?: throw NullPointerException("Received null value from db for a non null field")
