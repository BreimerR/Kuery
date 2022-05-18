@file:Suppress("FunctionName")

package libetal.libraries.kuery.sqlite.core.columns.extensions

import libetal.libraries.kuery.sqlite.core.columns.Text
import libetal.libraries.kuery.core.columns.extensions.LIKE
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.extensions.expressionBuilder

infix fun Text.startsWith(start: String): Expression = this LIKE "%$start"

infix fun Text.endsWith(end: String): Expression = this LIKE "$end%"

infix fun Text.contains(content: String) = this LIKE "%$content%"

infix fun Text.equals(value: String) = expressionBuilder(value, "==")

