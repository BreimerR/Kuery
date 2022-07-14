@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.expressions.BooleanExpression
import libetal.libraries.kuery.core.expressions.DecoratedExpression
import libetal.libraries.kuery.core.expressions.DecoratedStatementExpression
import libetal.libraries.kuery.core.expressions.Expression.Operators.EQUALS
import libetal.libraries.kuery.core.expressions.Expression.Operators.LIKE
import libetal.libraries.kuery.core.expressions.extensions.expressionBuilder
import libetal.libraries.kuery.core.statements.Select

infix fun <C : CharSequence, T : EntityColumn<C>> T.endsWith(end: C) =
    LIKE(end, "", "%")

infix fun <C : CharSequence, T : EntityColumn<C>> T.contains(content: C) =
    LIKE(content, "%", "%")

infix fun <C : CharSequence, T : EntityColumn<C>> T.equals(value: C) =
    expressionBuilder(value, EQUALS)

infix fun <C : CharSequence, T : EntityColumn<C>> T.equals(value: Boolean) =
    BooleanExpression(this, EQUALS, value)

fun <T : CharSequence> EntityColumn<T>.LIKE(like: T, prefix: String, postFix: String = "") =
    DecoratedExpression(this, LIKE, like, prefix, postFix)

infix fun <C : CharSequence> EntityColumn<C>.startsWith(like: C) =
    LIKE(like, "%")

infix fun <C : CharSequence> EntityColumn<C>.startsWith(like: Select) =
    DecoratedStatementExpression(this, LIKE, like, "%")
