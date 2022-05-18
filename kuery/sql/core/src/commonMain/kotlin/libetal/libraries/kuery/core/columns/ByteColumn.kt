package libetal.libraries.kuery.core.columns

import libetal.libraries.kuery.core.entities.Entity

class ByteColumn : Column<Byte> {

    constructor(
        name: String,
        table: Entity<*>,
    ) : super(name, table, false)

    constructor(
        name: String,
        table: Entity<*>,
        default: Byte
    ) : super(name, table, default)

    override val createSql: String
        get() = "`$name` BYTE"
}
