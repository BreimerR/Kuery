package libetal.libraries.kuery.core.expressions

abstract class AbstractExpression(var sql: String) {
    override fun toString(): String = sql
}
