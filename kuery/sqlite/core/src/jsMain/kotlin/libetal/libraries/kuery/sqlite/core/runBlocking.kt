package libetal.libraries.kuery.sqlite.core

import kotlinx.coroutines.CoroutineScope

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T = TODO("")