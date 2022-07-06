package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.expressions.Expression

interface WhereStatement {
    val wheres: MutableList<Pair<String, Any>>
}

interface FinalWhereStatement {

    val where: String

    val boundWhere: String

}