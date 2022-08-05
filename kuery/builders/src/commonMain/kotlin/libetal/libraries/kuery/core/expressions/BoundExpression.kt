package libetal.libraries.kuery.core.expressions

class BoundExpression(sql: String) : AbstractExpression(sql) {
    val boundValues by lazy {
        mutableListOf<BoundValue<*>>()
    }
}

