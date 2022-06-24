package libetal.libraries.kuery.sqlite.core.columns

import libetal.libraries.kuery.core.columns.CharacterSequence
import libetal.libraries.kuery.sqlite.core.entities.Entity

class Text : CharacterSequence<String> {

    constructor(
        name: String,
        size: Int = 55,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false,
        primary: Boolean = false,
        parser: (String?) -> String,
    ) : super(
        name,
        "`$name` TEXT",
        size,
        collate,
        uniqueOn,
        unique,
        primary,
        parser
    )

    constructor(
        name: String,
        default: String? = null,
        size: Int = 55,
        collate: String? = null,
        uniqueOn: String? = null,
        unique: Boolean = false,
        parser: (String?) -> String,
    ) : super(
        name,
        "`$name` TEXT",
        size,
        default,
        collate,
        uniqueOn,
        unique,
        parser
    )


    override fun String.defaultSql(): String = "'$this'"

}
