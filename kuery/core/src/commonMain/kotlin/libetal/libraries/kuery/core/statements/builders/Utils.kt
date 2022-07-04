package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

fun <T, E : Entity<T>> Array<out Column<*>>.toSql(entity: E): String =
    joinToString("`, `") { it.name }.let {
        "`$it`"
    }