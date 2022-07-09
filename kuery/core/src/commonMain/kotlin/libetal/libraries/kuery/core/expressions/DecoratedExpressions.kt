package libetal.libraries.kuery.core.expressions

abstract class DecoratedExpressions<L, R> : Expression<L, R> {

    private val prefix: String
    private val postfix: String

    constructor(
        left: L,
        operator: Operators,
        right: R,
        prefix: String = "",
        postfix: String = ""
    ) : super(left, operator, right) {
        this.prefix = prefix
        this.postfix = postfix
    }


}