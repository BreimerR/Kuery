package libetal.libraries.kuery.core.statements.results

interface Result {

    val error: Exception?

    val success
        get() = error == null

    @Deprecated("Success is most concerned state",ReplaceWith("!success"))
    val failed
        get() = !success

    val errorMessage
        get() = error?.message ?: ""

}