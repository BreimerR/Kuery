package libetal.libraries.kuery.core

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.entities.Entity
import kotlin.native.concurrent.ThreadLocal


/**
 * Objects are frozen in kotlin/native
 * The database instance is an object
 * on most cases and thus this value will
 * be frozen as well unless made global
 *
 * Affects JS
 **/
@ThreadLocal // This is mainly for concurrency in kotlin
val tableEntities by laziest {
    mutableMapOf<Entity<*>, MutableList<BaseColumn<*>>>()
}