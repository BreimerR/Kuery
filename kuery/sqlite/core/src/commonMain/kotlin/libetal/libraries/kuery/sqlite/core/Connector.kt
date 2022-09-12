package libetal.libraries.kuery.sqlite.core

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Connector
import libetal.libraries.kuery.sqlite.core.database.extensions.addListener
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
var connector: Connector? = null