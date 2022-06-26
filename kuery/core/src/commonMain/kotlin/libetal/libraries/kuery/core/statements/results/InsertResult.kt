package libetal.libraries.kuery.core.statements.results

class InsertResult(
    val into: String,
    // val columns: List<Int>
    error: RuntimeException? = null
) : Result(error)