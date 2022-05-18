package libetal.libraries.kuery.core.expressions

import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.columns.CharacterSequence
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.extensions.lessOrEqual as extensionLerOrEqual
import libetal.libraries.kuery.core.columns.extensions.startsWith as extStartsWith

class OperatorScope(expression: Expression, val operator: String) : StatementScope() {

    val expressions by lazy {
        mutableListOf<Expression>(expression)
    }

    val sql
        get() = expressions.joinToString(" $operator ")

    @Suppress("CovariantEquals")
    infix fun <T> Column<T>.equals(value: T) = value.equalsExpression.also {
        expressions += it
    }

    infix fun <T> Column<T>.lessOrEqual(value: T) = extensionLerOrEqual(value).also {
        expressions += it
    }

    infix fun CharacterSequence<*>.startsWith(value: kotlin.CharSequence) = extStartsWith(value).also {
        expressions += it
    }

    companion object {
        private const val TAG = "OperatorScope"
    }

}

