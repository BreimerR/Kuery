package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

/*open class SizedColumn<T, N : Number> : Column<T> {

    val size: N?

    constructor(
        name: String,
        baseSql: String,
        default: T? = null,
        size: N? = null,
        parser: (String?) -> T,
    ) : super(
        name,
        baseSql + (size?.let { "($it)" } ?: ""),
        default,
        parser
    ) {
        this.size = size
    }

    constructor(
        name: String,
        baseSql: String,
        size: N? = null,
        primary: Boolean,
        nullable: Boolean = !primary,
        parser: (String?) -> T,
    ) : super(
        name,
        baseSql + (size?.let { "($it)" } ?: ""),
        primary,
        nullable,
        parser
    ) {
        this.size = size
    }

    override val createSql: String by laziest {
        baseSql + primarySql + nullableSql
    }


}*/







