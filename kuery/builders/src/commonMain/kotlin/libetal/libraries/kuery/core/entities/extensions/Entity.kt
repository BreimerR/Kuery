package libetal.libraries.kuery.core.entities.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.TableEntity
import libetal.libraries.kuery.core.entities.ViewEntity

/**
 * Convenience use due to disruption
 * caused by function property definition
 **/
val <T> Entity<T>.name
    get() = toString()

val <T> Entity<T>.identifier
    get() = "`${toString()}`"

/**
 * Convenience use due to disruption
 * caused by function property definition
 **/
val <T> Entity<T>.type
    get() = when (this) {
        is TableEntity -> Entity.Type.TABLE
        is ViewEntity -> Entity.Type.VIEW
        else -> throw RuntimeException("Unrecognised database entity type ${this::class.simpleName}")
    }