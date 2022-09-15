package libetal.libraries.kuery.core.expressions;

import libetal.kotlin.laziest

abstract class Expression<L, R>(val left: L, val operator: Operators, val right: R) {

    constructor(left: L, operator: String, right: R) : this(left, Operators(operator), right)

    abstract val sql: String

    abstract val boundSql: String

    open val columnValues: List<*> by laziest {
        listOf(
            right
        )
    }

    enum class Operators(private val op: String) {
        EQUALS("="),
        GREATER(">"),
        LESSER("<"),
        AND("AND"),
        OR("OR"),
        LIKE("LIKE"),
        NOT_EQUALS("<>"),
        GREATER_OR_EQUALS(">="),
        LESS_OR_EQUALS("<=");

        override fun toString(): String = op

        companion object {
            operator fun invoke(operator: String) = when (operator) {
                EQUALS.op -> EQUALS
                GREATER.op -> GREATER
                LESSER.op -> LESSER
                AND.op -> AND
                OR.op -> OR
                GREATER_OR_EQUALS.op -> GREATER_OR_EQUALS
                LESS_OR_EQUALS.op -> LESS_OR_EQUALS
                else -> throw RuntimeException("Unexpected exception")
            }
        }
    }

    override fun toString(): String = sql

}