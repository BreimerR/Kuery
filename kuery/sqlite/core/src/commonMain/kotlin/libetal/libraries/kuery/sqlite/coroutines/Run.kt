package libetal.libraries.kuery.sqlite.coroutines

import kotlinx.coroutines.CoroutineScope

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T