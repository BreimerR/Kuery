package libetal.libraries.kuery.sqlite.core.database.listeners

import libetal.libraries.kuery.sqlite.core.database.Connector

interface ConnectorListener {
    fun onCreate(connector: Connector)
}