package libetal.libraries.kuery.core

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.columns.NumberColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*

abstract class Kuery<AbstractEntity : Entity<*>> {


    abstract val connection: Connector

    operator fun <ET, E : Entity<ET>> contains(entity: E): Boolean = entity in tableEntities

    infix fun <ET, E : Entity<ET>> add(entity: E) {
        if (entity in this) return

        tableEntities[entity] = mutableListOf()

    }

    abstract fun AbstractEntity.long(
        name: String = "",
        size: Long? = null,
        default: Long? = null,
        primary: Boolean = false
    ): Column<Long>

    /*Allow for primary:Boolean = false here*/
    abstract fun AbstractEntity.int(
        name: String = "",
        size: Int? = null,
        primary: Boolean = false,
        autoIncrement: Boolean = primary,
        default: Int? = null,
    ): BaseColumn<Int>

    abstract fun AbstractEntity.float(
        name: String,
        size: Float? = null,
        default: Float? = null,
        //     nullable: Boolean = default == null /*Could you insert the default value instead in case it's missing*/
    ): NumberColumn<Float>

    abstract fun AbstractEntity.char(name: String): Column<Char>

    abstract fun AbstractEntity.date(
        name: String,
        default: LocalDate? = null,
        format: String? = null
    ): GenericColumn<LocalDate>

    /**TODO
     * Default value consistent in most databases add here
     **/
    abstract fun AbstractEntity.string(
        name: String,
        size: Int = 55,
        default: String? = null,
        primary: Boolean = false
    ): Column<CharSequence>

    open fun AbstractEntity.nullableString(
        name: String,
        size: Int = 55,
        default: String? = null
    ): Column<CharSequence?> {
        TODO("")
    }

    abstract fun AbstractEntity.boolean(name: String = "", default: Boolean? = null): GenericColumn<Boolean>

    abstract infix fun <T : Result> execute(statement: Statement<T>)


    suspend infix fun Create<*, *>.query(
        collector: CreateResult.() -> Unit
    ) = query(this).collect(collector)

    abstract infix fun query(statement: Create<*, *>): Flow<CreateResult>

    suspend infix fun Select.query(
        collector: SelectResult.() -> Unit
    ) = query(this).collect(collector)

    suspend infix operator fun Select.invoke(collector: SelectResult.() -> Unit) = query(collector)

    abstract infix fun query(statement: Select): Flow<SelectResult>

    abstract suspend infix fun Select.execute(onExec: suspend SelectResult.() -> Unit)

    suspend infix fun Insert.query(
        collector: InsertResult.() -> Unit
    ) = query(this).collect(collector)

    abstract infix fun query(statement: Insert): Flow<InsertResult>

    suspend infix fun Delete.query(
        collector: DeleteResult.() -> Unit
    ) = query(this).collect(collector)

    abstract infix fun query(statement: Delete): Flow<DeleteResult>

    suspend infix fun Drop.query(
        collector: DropResult.() -> Unit
    ) = query(this).collect(collector)

    abstract infix fun query(statement: Drop): Flow<DropResult>

    suspend infix fun Update.query(
        collector: UpdateResult.() -> Unit
    ) = query(this).collect(collector)

    abstract infix fun query(statement: Update): Flow<UpdateResult>

    @Deprecated("Use doesn't make proper sense", ReplaceWith("collect(collector)"))
    suspend operator fun <T> Flow<T>.invoke(collector: (T) -> Unit) =
        collect(collector)

    companion object {
        const val NOT_NULL = "NOT NULL"
    }
}