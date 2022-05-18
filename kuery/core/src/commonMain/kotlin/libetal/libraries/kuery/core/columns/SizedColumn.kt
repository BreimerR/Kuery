package libetal.libraries.kuery.core.columns

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.expressions.Expression

abstract class SizedColumn<T, N : Number> : Column<T> {

    val size: N?

    constructor(
        name: String,
        table: Entity<*>,
        default: T? = null,
        size: N? = null
    ) : super(
        name,
        table,
        default
    ) {
        this.size = size
    }

    constructor(
        name: String,
        table: Entity<*>,
        size: N? = null,
        primary: Boolean
    ) : super(
        name,
        table,
        primary
    ) {
        this.size = size
    }

    abstract val unSizedSql: String

    override val createSql: String
        get() = unSizedSql + (size?.let { "($it)" } ?: "")

}







