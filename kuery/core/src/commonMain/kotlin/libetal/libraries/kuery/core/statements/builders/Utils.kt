package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity

fun <T, E : Entity<T>> Array<out EntityColumn<*>>.toSql(entity: E): String =
    joinToString("`, `") { it.name }.let {
        "`$it`"
    }