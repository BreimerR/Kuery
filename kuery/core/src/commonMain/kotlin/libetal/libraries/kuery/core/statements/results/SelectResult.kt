package libetal.libraries.kuery.core.statements.results

class SelectResult(
    val table: String,
    val columnName: String,
    val value: String?, // null if row is nullable
    error: RuntimeException? = null
) : Result(error)

