package libetal.libraries.kuery.core

abstract class DatabaseHandler {
    operator fun invoke(handler: (DatabaseResponse) -> Unit) {
        TODO("Handler response")
    }
}

abstract class DatabaseResponse

infix fun DatabaseHandler.then(consumer: () -> Unit): DatabaseHandler {
    TODO("Pending implementation")
}