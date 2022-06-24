package libetal.libraries.kuery.core.entities.extensions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.TableEntity

/**
 * Convenience use due to disruption
 * caused by function property definition
 **/
val <T> Entity<T>.name
    get() = getEntityName()

/**
 * Convenience use due to disruption
 * caused by function property definition
 **/
val <T> Entity<T>.type
    get() = when (this) {
        is TableEntity -> Entity.Type.TABLE
        else -> throw RuntimeException("Unrecognised database entity type")
    }