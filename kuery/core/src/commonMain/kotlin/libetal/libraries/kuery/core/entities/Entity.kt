package libetal.libraries.kuery.core.entities

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.statements.builders.InsertStatementBuilder

abstract class Entity<T> {

    /**
     * Implementation of this property as a function
     * will reduce override conflicts
     **/
    abstract fun getEntityName(): String

    /**
     * Implementation of this property as a function
     * will reduce override conflicts
     **/
    open fun entityTableType() = Type.TABLE

    operator fun invoke(vararg columns: EntityColumn<*>): InsertStatementBuilder<T, Entity<T>> =
        InsertStatementBuilder(this, *columns)


    enum class Type(val identifier: String) {
        TABLE("TABLE"),
        VIEW("VIEW");

        override fun toString(): String = identifier
    }

}


abstract class TableEntity<T> : Entity<T>()

abstract class ViewEntity<T> : Entity<T>()