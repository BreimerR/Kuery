package libetal.libraries.kuery.sqlite.core.database.extensions


import libetal.kotlin.laziest
import libetal.libraries.kuery.sqlite.core.database.Connector
import libetal.libraries.kuery.sqlite.core.database.listeners.ConnectorListener

/**
 * Can't have property in class
 * since it might be frozen at times
 **/
val libetal.libraries.kuery.core.Connector.listeners by laziest {
    mutableListOf<ConnectorListener>()
}

fun libetal.libraries.kuery.core.Connector.addListener(connector: ConnectorListener) {
    listeners.add(connector)
}
