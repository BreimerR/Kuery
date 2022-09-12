package libetal.libraries.kuery.core.statements.results

class DeleteResult(
    val table: String,
    override val error: Exception? = null
) : Result