@file:Suppress("FunctionName")

package libetal.libraries.kuery.sqlite.core.columns.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.columns.Text
import libetal.libraries.kuery.core.columns.extensions.LIKE
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.extensions.expressionBuilder


/**TODO
 * Move to core
 **/
infix fun <C : CharSequence, T : Column<C>> T.startsWith(start: String): Expression = this LIKE "%$start"

/**TODO
 * Move to core
 **/
infix fun <C : CharSequence, T : Column<C>> T.endsWith(end: String): Expression = this LIKE "$end%"


/**TODO
 * Move to core
 **/
infix fun <C : CharSequence, T : Column<C>> T.contains(content: C) = this LIKE "%$content%"

/**TODO
 * move this to core
 **/
infix fun <C : CharSequence, T : Column<C>> T.equals(value: C) = expressionBuilder(value, "==")

