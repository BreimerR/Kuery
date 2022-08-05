package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

class GenericColumn<T>(
    name: String,
    private val typeName: String,
    default: T? = null,
    nullable: Boolean = false,
    alias: String? = null,
    toSqlString: (T) -> String = { it.toString() },
    parser: (String?) -> T
) : BaseColumn<T>(
    name,
    "`$name` $typeName",
    default,
    nullable,
    toSqlString,
    alias,
    parser
) {

    @Suppress("UNCHECKED_CAST")
    override fun <C : Column<T>> copy(alias: String): C = GenericColumn(
        name,
        typeName,
        default,
        nullable,
        alias,
        toSqlString,
        parser
    ) as C

    override val sql by laziest {
        "$identifier $typeName$nullableSql$defaultSql"
    }

}






