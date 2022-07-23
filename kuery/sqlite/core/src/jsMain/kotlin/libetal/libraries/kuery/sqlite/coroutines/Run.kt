package libetal.libraries.kuery.sqlite.coroutines

import kotlinx.coroutines.CoroutineScope

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T = TODO("")