package libetal.libraries.kuery.mariadb

import libetal.kotlin.laziest
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
var connector: Connector? = null