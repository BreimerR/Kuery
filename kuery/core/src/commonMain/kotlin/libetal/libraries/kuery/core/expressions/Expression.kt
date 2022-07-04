package libetal.libraries.kuery.core.expressions;

import libetal.kotlin.laziest

abstract class Expression<T>(val left: T, val operator: String, val right: T) {

    abstract val sql: String

    abstract val boundSql: String

    enum class Operators(private val op: String) {
        EQUALS("="),
        GREATER(">"),
        LESSER("<"),
        AND("AND"),
        OR("OR"),
        LIKE("LIKE"),
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