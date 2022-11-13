package libetal.libraries.kuery.core.statements.results

class UpdateResult(
    val table: String,
    override val error: Exception? = null
) : Result