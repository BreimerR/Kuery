package libetal.libraries.kuery.sqlite.core.columns


import libetal.libraries.kuery.core.columns.SizedColumn
import libetal.libraries.kuery.sqlite.core.entities.Entity

class CharColumn : SizedColumn<Char, Int> {

    val collate: String?
    val uniqueOn: String?
    val unique: Boolean

    constructor(
        name: String,
        table: Entity<*, *, *>,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false
    ) : super(
        name,
        table,
        1,
        false
    ) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
    }

    constructor(
        name: String,
        table: Entity<*, *, *>,
        default: Char? = null,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false
    ) : super(
        name,
        table,
        default,
        1
    ) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique

    }

    override val unSizedSql: String
        get() = "`$name` TEXT"

    override fun Char.defaultSql(): String = this.toString()

}
