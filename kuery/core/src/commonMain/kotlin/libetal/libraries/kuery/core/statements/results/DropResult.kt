package libetal.libraries.kuery.core.statements.results

class DropResult(
    val table: String,
    error: Exception? = null
) : Result(error)