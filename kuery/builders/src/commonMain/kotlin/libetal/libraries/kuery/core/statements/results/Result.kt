package libetal.libraries.kuery.core.statements.results

interface Result {

    val error: Exception?
    val failed
        get() = error != null

    val errorMessage
        get() = error?.message ?: ""

}