package libetal.libraries.kuery.core.entities.extensions

import libetal.libraries.kuery.core.entities.Entity

/**
 * Convenience use due to disruption
 * caused by function property definition
 **/
val <T> Entity<T>.name
    get() = getEntityTableName()

/**
 * Convenience use due to disruption
 * caused by function property definition
 **/
val <T> Entity<T>.type
    get() = getEntityTableName()