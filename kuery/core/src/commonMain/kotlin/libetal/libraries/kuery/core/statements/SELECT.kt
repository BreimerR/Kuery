@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.statements.builders.SelectBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder
import libetal.libraries.kuery.core.statements.builders.StatementFactory


object SELECT : StatementFactory<Select<*, *>>() {

    operator fun invoke(vararg column: Column<*>) = SelectBuilder(
        if (column.isNotEmpty()) "`${column.joinToString("`,`") { it.identifier }}`" else "*",
        *column
    )

    infix fun <T, E : Entity<T>> ALL(from: E) =
        SelectStatementBuilder("*", entity = from)

    infix operator fun <T, E : Entity<T>> times(from: E) =
        ALL(from)

}

