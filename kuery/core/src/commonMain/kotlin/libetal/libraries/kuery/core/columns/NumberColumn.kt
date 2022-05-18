package libetal.libraries.kuery.core.columns

import libetal.libraries.kuery.core.entities.Entity

abstract class NumberColumn<N : Number> : SizedColumn<N, N> {
    constructor(
        name: String,
        table: Entity<*>,
        size: N? = null,
        primary: Boolean = false
    ) : super(name, table, size, primary)

    constructor(
        name: String,
        table: Entity<*>,
        size: N? = null,
        default: N? = null
    ) : super(name, table, default, size)


}
