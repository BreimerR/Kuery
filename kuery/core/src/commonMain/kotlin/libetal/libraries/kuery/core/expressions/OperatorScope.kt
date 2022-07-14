package libetal.libraries.kuery.core.expressions

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.columns.extensions.lessOrEqual as extensionLerOrEqual
import libetal.libraries.kuery.core.columns.extensions.startsWith as extStartsWith

class OperatorScope(expression: Expression<*, *>, val operator: String) : StatementScope {

    var expression: Expression<*, *> = expression
        private set

    val sql
        get() = expression.toString()

    @Suppress("CovariantEquals") // TODO: This isn't the best due to operator problems
    infix fun <T> EntityColumn<T>.equals(value: T) = equivalent(value)

    /**
     * TODO:
     * This might be best for the api and should be the
     * recommended use case
     **/
    infix fun <T> EntityColumn<T>.equivalent(value: T) {
        expression + value.equalsExpression
    }

    infix fun <T> EntityColumn<T>.lessOrEqual(value: T) = extensionLerOrEqual(value).also {
        expression + it
    }

    infix fun <C : CharSequence> EntityColumn<C>.startsWith(value: C) = extStartsWith(value).also {
        expression + it
    }

    private operator fun Expression<*, *>?.plus(right: Expression<*, *>) {
        expression = this?.let {
            JoinedExpression(it, Expression.Operators(this@OperatorScope.operator), right)
        } ?: right
    }

    companion object {
        private const val TAG = "OperatorScope"
    }

}

