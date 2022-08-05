package libetal.libraries.kuery.core.statements.results

class DeleteResult(
    val table: String,
    error: Exception? = null
) : Result(error)