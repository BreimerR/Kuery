@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.statements.builders.SelectPreBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder
import libetal.libraries.kuery.core.statements.builders.StatementFactory
import libetal.libraries.kuery.core.tableEntities


object SELECT : StatementFactory<Select<*, *>>() {

    operator fun invoke(vararg column: Column<*>) = SelectPreBuilder(
        *column
    )


    /**
     * TODO:
     * This is something totally different from SelectPreBuilder
     **/
    infix fun AVG(column: Column<*>) {

    }

    infix fun <T, E : Entity<T>> ALL(from: E) =
        SelectStatementBuilder(entity = from, columns = tableEntities[from]?.toTypedArray() ?: arrayOf())

    infix operator fun <T, E : Entity<T>> times(from: E) =
        ALL(from)

}

