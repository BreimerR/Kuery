package libetal.libraries.kuery.core.statements.builders

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Insert

class InsertStatementBuilder<T, E : Entity<T>>(val entity: E, vararg val columns: BaseColumn<*>) :
    EntityStatementBuilder<T, E, Insert> {


    private val map by laziest {
        mutableMapOf<String, MutableList<String>>()
    }

    val columnsSQL by laziest {
        """(`${map.keys.joinToString("`, `")}`)"""
    }

    infix fun <C> BaseColumn<C>.set(value: C) {

        val values = map[name] ?: mutableListOf<String>().also {
            map[name] = it
        }

        values += value.sqlString

    }

    fun build() = Insert(entity, *columns)

    companion object {
        private const val TAG = "InsertStatementBuilder"
    }

}