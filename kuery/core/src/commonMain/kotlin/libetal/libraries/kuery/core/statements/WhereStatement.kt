package libetal.libraries.kuery.core.statements

interface WhereStatement {
    val wheres: MutableList<Pair<String, Any>>
}