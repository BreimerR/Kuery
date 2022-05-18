package libetal.libraries.kuery.sqlite.core.database.extensions


import libetal.libraries.kuery.sqlite.core.database.Connector
import libetal.libraries.kuery.sqlite.core.database.listeners.ConnectorListener

/**
 * Can't have property in class
 * since it might be frozen at times
 **/
val Connector.listeners by lazy {
    mutableListOf<ConnectorListener>()
}

fun Connector.addListener(connector: ConnectorListener) {
    listeners.add(connector)
}
