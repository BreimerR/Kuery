package libetal.libraries.kuery.core.statements.results

class DeleteResult(
    val table: String,
    // val columns: List<Int>
    error: RuntimeException? = null
) : Result(error)