package libetal.libraries.kuery.core

import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.delegates.ColumnDelegate
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.core.statements.Create
import libetal.libraries.kuery.core.statements.Statement

/**
 * Objects are frozen in kotlin/native
 * The database instance is an object
 * on most cases and thus this value will
 * be frozen as well unless made global
 *
 * Affects JS
 **/
val <AbstractEntity : Entity<*>> Kuery<AbstractEntity>.tableEntities by laziest {
    mutableMapOf<Entity<*>, MutableList<Column<*>>>()
}

abstract class Kuery<AbstractEntity : Entity<*>> {

    operator fun <ET, E : Entity<ET>> contains(entity: E): Boolean = entity in tableEntities

    infix fun <ET, E : Entity<ET>> add(entity: E) {
        if (entity in this) return

        tableEntities[entity] = mutableListOf()

    }

    operator fun get(entity: AbstractEntity) = tableEntities[entity] ?: mutableListOf<Column<*>>().also {
        tableEntities[entity] = it
    }

    fun <T, C : Column<T>> column(converter: () -> T = { TODO() }): ColumnDelegate<T, C, AbstractEntity> {
        TODO()
    }

    abstract fun AbstractEntity.long(name: String = ""): Column<Long>

    /*Allow for primary:Boolean = false here*/
    abstract fun AbstractEntity.int(name: String = "", primary: Boolean = false): Column<Int>

    abstract fun AbstractEntity.float(name: String = ""): Column<Float>

    abstract fun AbstractEntity.char(name: String): Column<Char>

    abstract fun AbstractEntity.date(name: String): Column<LocalDate>

    /**TODO
     * Default value consistent in most databases add here
     **/
    abstract fun AbstractEntity.string(name: String, size: Int = 55, default: String? = null): Column<String>

    abstract fun AbstractEntity.boolean(name: String = ""): Column<Boolean>

    abstract infix fun <T, E : Entity<T>> execute(statement: Statement<T, E>)

    /**
     * Do not remove the entity
     * context requirement yet
     **/
    @Suppress("UNCHECKED_CAST")
    fun <ColumnType, C : Column<ColumnType>> AbstractEntity.registerColumn(
        name: String,
        initializer: AbstractEntity.(columnName: String) -> C
    ) = this@Kuery[this].let { columns ->
        val columnName = name.ifBlank { null } ?: throw RuntimeException("Can't have an empty column name")
        columns.firstOrNull { it.name == columnName } ?: initializer(columnName).also { column ->
            columns.add(column)
        }
    } as C

    abstract fun onCreate()

    @Suppress("FunctionName")
    infix fun <Class, E : Entity<Class>> CREATE.TABLE(entity: E) = Create(entity, this@Kuery)

}