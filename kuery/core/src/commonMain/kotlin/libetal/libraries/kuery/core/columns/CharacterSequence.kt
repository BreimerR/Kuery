package libetal.libraries.kuery.core.columns

import libetal.libraries.kuery.core.entities.Entity

abstract class CharacterSequence<C : CharSequence> : SizedColumn<C, Int> {

    val collate: String?
    val uniqueOn: String?
    val unique: Boolean

    constructor(
        name: String,
        table: Entity<*>,
        size: Int = 55,
        collate: String?,
        uniqueOn: String? = null,
        unique: Boolean = false,
        primary: Boolean = false
    ) : super(name, table, size, primary) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
    }

    constructor(
        name: String,
        table: Entity<*>,
        size: Int = 55,
        default: C? = null,
        collate: String?,
        uniqueOn: String? = null,
        unique: Boolean = false
    ) : super(name, table, default, size) {
        this.collate = collate
        this.uniqueOn = uniqueOn
        this.unique = unique
    }

}