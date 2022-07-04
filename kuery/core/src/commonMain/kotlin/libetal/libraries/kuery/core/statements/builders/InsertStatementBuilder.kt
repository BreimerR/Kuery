package libetal.libraries.kuery.core.statements.builders

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.Insert

class InsertStatementBuilder<T, E : Entity<T>>(entity: E) :
    EntityStatementBuilder<T, E, Insert<T, E>>("INSERT INTO `${entity.name}`", entity = entity) {

    private val map by laziest {
        mutableMapOf<String, MutableList<String>>()
    }

    infix fun <C> Column<C>.set(value: C) {

        val values = map[name] ?: mutableListOf<String>().also {
            map[name] = it
        }

        values += value.sqlString

    }

    val columnsSQL
        get() = """(`${map.keys.joinToString("`, `")}`)"""

    val valuesSql
        get():String {
            val sql = mutableListOf<String>()

            var k = 0
            val keys = map.keys.toList()
            if (keys.isEmpty())
                throw RuntimeException("Need to set values inside INSERT INTO TableName VALUES { ${entity::class.simpleName}.columnName set compatibleColumnTypeValue }")
            val lK = map.keys.size
            var v = 0

            val pending = mutableListOf<String>()

            while (true) {

                val retrieved = map.getOrElse(keys[k]) { null }?.getOrNull(v)?.ifBlank { null }

                retrieved?.let {
                    pending += it
                }

                if (k == lK - 1) {
                    if (pending.isEmpty()) break
                    sql += "(${pending.joinToString(", ")})"
                    k = 0
                    v++
                    pending.clear()
                } else {
                    k++
                }

            }

            return sql.joinToString(", ")

        }

    override fun build(extras: String) = Insert("$sql $extras", entity).also {
        it.columns.addAll(columns)
    }

    companion object {
        private const val TAG = "InsertStatementBuilder"
    }

}