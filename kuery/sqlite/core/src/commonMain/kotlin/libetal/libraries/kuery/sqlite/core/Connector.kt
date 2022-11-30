package libetal.libraries.kuery.sqlite.core

import libetal.libraries.kuery.core.Connector
import kotlin.native.concurrent.ThreadLocal

/**TODO
 * The new kotlin memory management model could be supporting this
 * the error requiring this to be defined here and there is need to
 * confirm this every now and then as items are no longer required to be frozen
 **/
@ThreadLocal
var connector: Connector? = null