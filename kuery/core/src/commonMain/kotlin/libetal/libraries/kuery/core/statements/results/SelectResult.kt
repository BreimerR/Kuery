package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

class SelectResult(
    val row: MutableMap<String, MutableMap<String, Any?>>, // null if row is nullable
    error: RuntimeException? = null
) : Result(error) {
    fun <T> get(tableName: String, columnName: String): T {
        val table = row[tableName] ?: throw RuntimeException("Requesting for a table that wasn't queried")

        @Suppress("UNCHECKED_CAST")
        return try {
            table[columnName] as T
        } catch (e: ClassCastException) {
            throw RuntimeException("Invalid value the column requested isn't of the requested type")
        }

    }

    operator fun <T> get(column: Pair<Entity<*>, Column<*>>): T = get(column.first.toString(), column.second.name)

}

