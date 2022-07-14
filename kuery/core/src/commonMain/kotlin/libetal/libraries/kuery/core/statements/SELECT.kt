@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.columns.FunctionColumn
import libetal.libraries.kuery.core.statements.builders.SelectPreBuilder
import libetal.libraries.kuery.core.statements.builders.SelectStatementBuilder
import libetal.libraries.kuery.core.statements.builders.StatementFactory
import libetal.libraries.kuery.core.tableEntities


object SELECT : StatementFactory<Select>() {

    operator fun invoke(vararg column: EntityColumn<*>) = SelectPreBuilder(
        *column
    )


    /**
     * TODO:
     * This is something totally different from SelectPreBuilder
     **/
    infix fun <T> AVG(column: EntityColumn<T>) = SelectPreBuilder(
        FunctionColumn("AVG", column, {
            it?.toLongOrNull() ?: 0
        })
    )

    /**
     * TODO
     * COUNT DISTINCT not supported yet
     **/
    infix fun <T> COUNT(column: EntityColumn<T>) = SelectPreBuilder(
        FunctionColumn("COUNT", column, {
            it?.toLongOrNull() ?: 0
        })
    )

    infix fun <T, E : Entity<T>> ALL(from: E) =
        SelectStatementBuilder(entity = from, columns = tableEntities[from]?.toTypedArray() ?: arrayOf())

    infix operator fun <T, E : Entity<T>> times(from: E) =
        ALL(from)

}

