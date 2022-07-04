@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.expressions.DecoratedExpression
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.Expression.Operators.*
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.extensions.expressionBuilder

infix fun <C : CharSequence, T : Column<C>> T.endsWith(end: C) =
    DecoratedExpression(this, LIKE, end, postfix = "%")

infix fun <C : CharSequence, T : Column<C>> T.contains(content: C) =
    DecoratedExpression(this, LIKE, content, "%", "%")


infix fun <C : CharSequence, T : Column<C>> T.equals(value: C) =
    expressionBuilder(value, EQUALS)

infix fun <T : CharSequence> Column<T>.LIKE(like: String) =
    SimpleExpression<T>(name, LIKE, like)

infix fun <C : CharSequence> Column<C>.startsWith(like: C) =
    DecoratedExpression(this, LIKE, like, "%")
