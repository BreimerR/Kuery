package libetal.libraries.kuery.core.statements.results

class UpdateResult(
    val table: String,
    error: RuntimeException? = null
) : Result(error)