package libetal.libraries.kuery.core.statements

enum class Existence(val state: Boolean) {
    EXISTS(true),
    NOT_EXISTS(false);

    companion object {
        operator fun invoke(state: Boolean) = when (state) {
            true -> EXISTS
            false -> NOT_EXISTS
        }
    }
}