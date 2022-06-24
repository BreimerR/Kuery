package libetal.libraries.kuery.core

interface Connector {
    fun query(sqlStatement: String): Boolean
}