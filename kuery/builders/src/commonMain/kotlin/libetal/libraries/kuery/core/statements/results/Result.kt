package libetal.libraries.kuery.core.statements.results

abstract class Result(val error: Exception? = null) {
    val failed
        get() = error != null

    val errorMessage
        get() = error?.message ?: ""
}