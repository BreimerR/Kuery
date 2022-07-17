package libetal.libraries.kuery.core

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.columns.delegates.ColumnDelegate
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
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
    mutableMapOf<Entity<*>, MutableList<EntityColumn<*>>>()
}

abstract class Kuery<AbstractEntity : Entity<*>> {

    operator fun <ET, E : Entity<ET>> contains(entity: E): Boolean = entity in tableEntities

    infix fun <ET, E : Entity<ET>> add(entity: E) {
        if (entity in this) return

        tableEntities[entity] = mutableListOf()

    }

    operator fun get(entity: AbstractEntity) = tableEntities[entity] ?: mutableListOf<EntityColumn<*>>().also {
        tableEntities[entity] = it
    }

    fun <T, C : EntityColumn<T>> column(converter: () -> T = { TODO() }): ColumnDelegate<T, C, AbstractEntity> {
        TODO()
    }

    abstract fun AbstractEntity.long(name: String = ""): EntityColumn<Long>

    /*Allow for primary:Boolean = false here*/
    abstract fun AbstractEntity.int(name: String = "", size: Int? = null, primary: Boolean = false): EntityColumn<Int>

    abstract fun AbstractEntity.float(name: String, size: Float? = null, default: Float? = null): EntityColumn<Float>

    abstract fun AbstractEntity.char(name: String): EntityColumn<Char>

    abstract fun AbstractEntity.date(name: String): EntityColumn<LocalDate>

    /**TODO
     * Default value consistent in most databases add here
     **/
    abstract fun AbstractEntity.string(name: String, size: Int = 55, default: String? = null): EntityColumn<String>
    open fun AbstractEntity.nullableString(name: String, size: Int = 55, default: String? = null): EntityColumn<String?> {
        TODO("")
    }

    abstract fun AbstractEntity.boolean(name: String = "", default: Boolean? = null): EntityColumn<Boolean>

    abstract infix fun <T : Result> execute(statement: Statement<T>)

    /**
     * Do not remove the entity
     * context requirement yet
     **/
    @Suppress("UNCHECKED_CAST")
    fun <ColumnType, C : EntityColumn<ColumnType>> AbstractEntity.registerColumn(
        name: String,
        initializer: AbstractEntity.(columnName: String) -> C
    ) = this@Kuery[this].let { columns ->
        val columnName = name.ifBlank { null } ?: throw RuntimeException("Can't have an empty column name")
        columns.firstOrNull { it.name == columnName } ?: initializer(columnName).also { column ->
            columns.add(column)
        }
    } as C

    abstract infix fun Create<*, *>.query(
        collector: CreateResult.() -> Unit
    ): Unit

    abstract infix fun Select.query(
        collector: SelectResult.() -> Unit
    ): Unit

    abstract infix fun Insert.query(
        collector: InsertResult.() -> Unit
    ): Unit

    abstract infix fun Delete.query(
        collector: DeleteResult.() -> Unit
    ): Unit

    abstract infix fun Drop.query(
        collector: DropResult.() -> Unit
    ): Unit

    abstract infix fun Update.query(
        collector: UpdateResult.() -> Unit
    ): Unit

    abstract fun onCreate()

    suspend operator fun <T> Flow<T>.invoke(collector: (T) -> Unit) =
        collect(collector)

    companion object {
        const val NOT_NULL = "NOT NULL"
    }
}