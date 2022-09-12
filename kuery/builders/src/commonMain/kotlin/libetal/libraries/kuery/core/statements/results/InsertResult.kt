package libetal.libraries.kuery.core.statements.results

class InsertResult(
    val into: String,
    override val error: Exception? = null
) : Result