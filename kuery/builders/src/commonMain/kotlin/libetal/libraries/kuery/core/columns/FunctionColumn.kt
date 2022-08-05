package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

class FunctionColumn<T, R>(
    private val func: String,
    private val column: BaseColumn<T>,
    override val parser: (String?) -> R,
    override val alias: String? = null
) : Column<R> {

    override val nullable: Boolean
        get() = false

    override val name: String // TODO: Still debating if this will or won't cause confusion but looks like it will
        get() = alias ?: column.name

    private val baseIdentifier by laziest {
        "$func(${column.identifier})"
    }

    override val identifier: String by laziest {
        val usableAlias = alias?.let {
            " as $it"
        } ?: ""

        "$baseIdentifier$usableAlias"
    }

    override val sql: String by laziest {
        "$baseIdentifier as $name"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Column<R>> copy(alias: String): C = FunctionColumn(
        func,
        column,
        parser,
        alias
    ) as C

}