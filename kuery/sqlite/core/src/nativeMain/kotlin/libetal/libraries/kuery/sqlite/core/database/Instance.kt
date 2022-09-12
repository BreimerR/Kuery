package libetal.libraries.kuery.sqlite.core.database

import libetal.libraries.kuery.core.Connector

@ThreadLocal
var INSTANCE: Connector? = null