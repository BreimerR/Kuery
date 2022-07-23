package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

/*Should this be for Strings or Char and String?*/
class CharSequenceColumn<T,R>(
    name: String,
    private val typeName: String,
    default: T? = null,
    override val size: R? = null,
    override val primary: Boolean = false,
    nullable: Boolean = primary,
    alias: String? = null,
    parser: (String?) -> T
) : BaseColumn<T>(
    name = name,
    "`$name` typeName",
    default = default,
    nullable = primary || nullable,
    toSqlString = { "'${it.toString()}'" },
    alias = alias,
    parser = parser
), PrimaryColumn, SizedColumn<R> {

    override val sql by laziest {
        "$identifier $typeName$sizedSql$nullableSql$defaultSql"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Column<T>> copy(alias: String): C = CharSequenceColumn(
        name,
        typeName,
        default,
        size,
        primary,
        nullable,
        alias,
        parser
    ) as C

}