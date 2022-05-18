package libetal.libraries.kuery.core.columns

import libetal.libraries.kuery.core.entities.Entity

class VarChar : SizedColumn<String, Int> {

    private val collate: String
    private val uniqueOn: String
    private val unique: Boolean
    private val autoIncrement: Boolean

    constructor(
        name: String,
        table: Entity<*>,
        size: Int = 55,
        collate: String = "",
        uniqueOn: String = "",
        unique: Boolean = false,
        primary: Boolean = false,
        autoIncrement: Boolean = false
    ) : super(name, table, size, primary) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
        this.autoIncrement = autoIncrement
    }

    constructor(
        name: String,
        table: Entity<*>,
        default: String = "",
        size: Int = 55,
        collate: String = "",
        uniqueOn: String = "",
        unique: Boolean = false,
        autoIncrement: Boolean = false
    ) : super(name, table, default, size) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
        this.autoIncrement = autoIncrement
    }

    override val createSql: String
        get() = "`$name` VARCHAR($size)"
}