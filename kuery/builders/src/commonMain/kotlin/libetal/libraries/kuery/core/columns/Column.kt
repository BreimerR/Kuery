package libetal.libraries.kuery.core.columns

interface Column<T> {
    val identifier: String
    val name: String
    val parser: (String?) -> T
    val nullable: Boolean
    val sql: String
    val alias: String?
    infix fun parse(value: String?): T = parser(value)

    infix fun <C : Column<T>> copy(alias: String): C

}