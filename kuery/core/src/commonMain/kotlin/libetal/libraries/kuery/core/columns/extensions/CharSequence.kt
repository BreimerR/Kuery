@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.columns.extensions

import libetal.libraries.kuery.core.columns.CharacterSequence
import libetal.libraries.kuery.core.expressions.Expression

infix fun CharacterSequence<*>.LIKE(like: String): Expression = Expression("""`$identifier` LIKE '$like'""")

infix fun CharacterSequence<*>.startsWith(like: kotlin.CharSequence): Expression = Expression("""`$identifier` LIKE '%$like'""")