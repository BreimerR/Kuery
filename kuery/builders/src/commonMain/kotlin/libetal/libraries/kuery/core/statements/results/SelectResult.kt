package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.exceptions.KueryException

class SelectResult(
    val results: Map<Column<*>, String?>,
    override val error: Exception? = null
) : Result {

    val <T> Column<T>.value: T
        get() = this@SelectResult[this]

    val Column<CharSequence>.stringValue: String
        get() = value.toString()

    operator fun <T> get(column: Column<T>): T {
        val result = if (column in results.keys) results[column] else throw UnQueriedColumnResultRequestException(column)
 
        return column.parser(result)
    }

    /**
     * This might cause unnecessary errors  at runtime
     * and avoiding it is best?
     **/
    @ExperimentalMultiplatform
    operator fun <T> get(id: Int): T {

        val col =
            if (id > 0 && id < results.keys.size) results.keys.toList()[id] else throw KueryException("Invalid column id used")

        return get(
            try {
                col as Column<T>
            } catch (e: Exception) {
                throw KueryException("Invalid column type passed")
            }
        )

    }


}
