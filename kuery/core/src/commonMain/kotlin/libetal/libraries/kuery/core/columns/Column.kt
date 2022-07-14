package libetal.libraries.kuery.core.columns

interface Column<T> {
    val identifier: String
    val name: String
    val parser: (String?) -> T
    val nullable: Boolean
    fun parse(value: String?): T = parser(value)

}