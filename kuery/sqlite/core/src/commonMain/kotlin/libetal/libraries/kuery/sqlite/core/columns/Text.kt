package libetal.libraries.kuery.sqlite.core.columns

import libetal.libraries.kuery.core.columns.CharacterSequence
import libetal.libraries.kuery.sqlite.core.entities.Entity

class Text : CharacterSequence<String> {

    constructor(
        name: String,
        table: Entity<*, *, *>,
        size: Int = 55,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false,
        primary: Boolean = false,
    ) : super(
        name,
        table,
        size,
        collate,
        uniqueOn,
        unique,
        primary
    )

    constructor(
        name: String,
        table: Entity<*, *, *>,
        default: String? = null,
        size: Int = 55,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false
    ) : super(
        name,
        table,
        size,
        default,
        collate,
        uniqueOn,
        unique
    )

    override val unSizedSql: String
        get() = "`$name` TEXT"

    override fun String.defaultSql(): String = "'$this'"

}
