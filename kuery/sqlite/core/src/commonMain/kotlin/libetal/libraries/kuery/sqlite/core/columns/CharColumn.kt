package libetal.libraries.kuery.sqlite.core.columns


import libetal.libraries.kuery.core.columns.SizedColumn
import libetal.libraries.kuery.sqlite.core.entities.Entity

class CharColumn : SizedColumn<Char, Int> {

    val collate: String?
    val uniqueOn: String?
    val unique: Boolean

    constructor(
        name: String,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false,
        parser: (String?) -> Char,
    ) : super(
        name,
        "`$name` TEXT",
        1,
        false,
        false,
        parser
    ) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
    }

    constructor(
        name: String,
        default: Char? = null,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false,
        parser: (String?) -> Char
    ) : super(
        name,
        "`$name` TEXT",
        default,
        1,
        parser
    ) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique

    }

    override fun Char.defaultSql(): String = this.toString()

}
