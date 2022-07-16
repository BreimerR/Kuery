package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

class FunctionColumn<T, R>(
    private val func: String,
    private val column: EntityColumn<T>,
    override val parser: (String?) -> R,
    private val alias: String? = null
) : Column<R> {

    override val nullable: Boolean
        get() = false

    override val name: String // TODO: Still debating if this will or won't cause confusion but looks like it will
        get() = alias ?: column.name

    override val identifier: String by laziest {
        val usableAlias = alias?.let {
            " as $it"
        } ?: ""
        "$func(${column.identifier})$usableAlias"
    }

}