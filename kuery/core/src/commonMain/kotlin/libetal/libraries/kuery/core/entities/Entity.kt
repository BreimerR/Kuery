package libetal.libraries.kuery.core.entities

import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.statements.builders.InsertStatementBuilder

abstract class Entity<T> {

    /**
     * Implementation of this property as a function
     * will reduce override conflicts
     **/
     open fun getEntityName(): String = ""

    operator fun invoke(vararg columns: BaseColumn<*>): InsertStatementBuilder<T, Entity<T>> =
        InsertStatementBuilder(this, *columns)


    enum class Type(val identifier: String) {
        TABLE("TABLE"),
        VIEW("VIEW");

        override fun toString(): String = identifier
    }

    abstract override fun toString(): String

}


