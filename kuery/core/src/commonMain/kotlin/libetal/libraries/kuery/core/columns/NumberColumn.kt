package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

open class NumberColumn<N : Number> : SizedColumn<N, N> {
    constructor(
        name: String,
        defaultSql: String,
        size: N? = null,
        primary: Boolean = false,
        nullable: Boolean = !primary,
        parser: (String?) -> N,
    ) : super(name, defaultSql, size, primary, nullable, parser)

    constructor(
        name: String,
        defaultSql: String,
        size: N? = null,
        default: N? = null,
        parser: (String?) -> N,
    ) : super(name, defaultSql, default, size, parser)

    override val createSql: String by laziest {
        super.createSql + primarySql + defaultSql
    }

    override fun N.defaultSql(): String = toString()

}
