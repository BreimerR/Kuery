package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.extensions.lessOrEqual as extensionLerOrEqual
import libetal.libraries.kuery.core.columns.extensions.startsWith as extStartsWith

class OperatorScope(private var expression: Expression?, val operator: String) : StatementScope {

    val sql
        get() = expression?.toString() ?: throw RuntimeException("Inappropriate OperatorScope Used")

    @Suppress("CovariantEquals") // TODO: This isn't the best due to operator problems
    infix fun <T> Column<T>.equals(value: T) = equivalent(value)

    /**
     * TODO:
     * This might be best for the api and should be the
     * recommended use case
     **/
    infix fun <T> Column<T>.equivalent(value: T) {
        expression + value.equalsExpression
    }

    infix fun <T> Column<T>.lessOrEqual(value: T) = extensionLerOrEqual(value).also {
        expression + it
    }

    infix fun <C : CharSequence> Column<C>.startsWith(value: CharSequence) = extStartsWith(value).also {
        expression + it
    }

    private operator fun Expression?.plus(right: Expression) {
        expression = this?.let {
            JoinedExpression(it, Expression.Operators(operator), right)
        } ?: right
    }

    companion object {
        private const val TAG = "OperatorScope"
    }

}

