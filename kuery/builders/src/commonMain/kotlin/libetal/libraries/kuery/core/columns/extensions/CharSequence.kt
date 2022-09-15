@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.expressions.BooleanExpression
import libetal.libraries.kuery.core.expressions.DecoratedExpression
import libetal.libraries.kuery.core.expressions.DecoratedStatementExpression
import libetal.libraries.kuery.core.expressions.Expression.Operators.*
import libetal.libraries.kuery.core.expressions.SimpleExpression
import libetal.libraries.kuery.core.expressions.extensions.expressionBuilder
import libetal.libraries.kuery.core.statements.Select

infix fun <C : CharSequence, T : BaseColumn<C>> T.endsWith(end: C) =
    LIKE(end, "%", "")

infix fun <C : CharSequence, T : BaseColumn<C>> T.contains(content: C) =
    LIKE(content, "%", "%")

/*infix fun <C : CharSequence, T : BaseColumn<C>> T.equals(value: C) =
    expressionBuilder(value, EQUALS)*/

infix fun BaseColumn<CharSequence>.equals(value: Boolean) =
    SimpleExpression(this, if (value) NOT_EQUALS else EQUALS, "")

fun <T : CharSequence> BaseColumn<T>.LIKE(like: T, prefix: String, postFix: String = "") =
    DecoratedExpression(this, LIKE, like, prefix, postFix)

infix fun <C : CharSequence> BaseColumn<C>.startsWith(like: C) =
    LIKE(like, "", "%")

infix fun <C : CharSequence> BaseColumn<C>.startsWith(like: Select) =
    DecoratedStatementExpression(this, LIKE, like, "", "%")
