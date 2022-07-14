package libetal.libraries.kuery.core.columns.delegates

import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity
import kotlin.reflect.KProperty

class ColumnDelegate<ColumnType, C : EntityColumn<ColumnType>, AbstractEntity : Entity<*>>(
    val database: Kuery<AbstractEntity>,
    val name: String,
    val initialize: AbstractEntity.(columnName: String) -> C
) {

    /**
     * This design is flawed
     * It requires the property to be called at least
     * once inorder to utilize this.
     *
     * But there haven't found a proper appealing way
     * to do this at runtime.
     *
     * ### Solution
     * 1. Compiler plugin
     *     > This will cause for a rewrite of some internal classes.  i.e. This class won't be required
     *     > But has better source code size and design usage
     * 2. Make Name a required property and utilize solution
     *    > AbstractEntity.delegateBuilder
     **/
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(entity: AbstractEntity, property: KProperty<*>): C = database[entity].let { columns ->

        val columnName = name.trim().ifBlank { property.name }

        columns.firstOrNull { it.name == columnName } ?: entity.initialize(columnName).also {
            columns.add(it)
        }

    } as C

}

