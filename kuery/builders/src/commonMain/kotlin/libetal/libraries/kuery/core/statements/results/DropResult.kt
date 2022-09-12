package libetal.libraries.kuery.core.statements.results

class DropResult(
    val table: String,
    override val error: Exception? = null
) : Result