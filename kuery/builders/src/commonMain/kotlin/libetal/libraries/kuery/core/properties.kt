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
@ThreadLocal // This is mainly for concurrency in kotlin-native
val tableEntities: MutableMap<Entity<*>, MutableList<BaseColumn<*>>> by laziest {
    mutableMapOf()
}

val <T> Entity<T>.columns
    get() = tableEntities[this] ?: mutableListOf<BaseColumn<*>>().also {
        tableEntities[this] = it
    }

/**
 * Do not remove the entity
 * context requirement yet
 **/
fun <T, ColumnType, C : BaseColumn<ColumnType>> Entity<T>.getOrRegisterColumn(name: String, initializer: Entity<T>.() -> C): C {

    val columnName = name.ifBlank { null } ?: throw RuntimeException("Can't have an empty column name")

    val column = columns.firstOrNull { it.name == columnName } ?: initializer().also { column ->
        columns.add(column)
    }

    @Suppress("UNCHECKED_CAST")
    return column as C

}

