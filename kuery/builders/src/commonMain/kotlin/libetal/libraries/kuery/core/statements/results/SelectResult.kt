package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.columns.Column

class SelectResult(
    val row: List<Any?>,
    error: Exception? = null,
    vararg columns: Column<*>
) : Result(error) {

    val columns by lazy {
        buildMap {
            columns.forEachIndexed { i, col ->
                this += col.name to i
            }
        }
    }

    val <T> Column<T>.value: T
        get() = this@SelectResult[this]

    operator fun <T> get(column: Column<*>) = get<T>(columns[column.name] ?: throw UnQueriedColumnResultRequest(column))

    operator fun <T> get(id: Int): T {
        val value = row[id]

        @Suppress("UNCHECKED_CAST")
        return try {
            value as T
        } catch (e: ClassCastException) {
            throw RuntimeException("Invalid value the column requested isn't of the requested type")
        }
    }

}

