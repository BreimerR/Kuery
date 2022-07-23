package libetal.libraries.kuery.core.statements.results

class InsertResult(
    val into: String,
    error: Exception? = null
) : Result(error)