@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.expressions

class Expression(sql: String) : AbstractExpression(sql) {
    constructor(column: String, operator: String, value: String) : this("`$column` $operator $value")
    constructor(column: String, operator: Operator, value: String) : this(column, operator.toString(), value)

    enum class Operator(private val op: String) {
        EQUALS("=="),
        GREATER(">"),
        LESSER("<"),
        GREATER_OR_EQUALS(">="),
        LESS_OR_EQUALS("<=");

        override fun toString(): String = op
    }

}


