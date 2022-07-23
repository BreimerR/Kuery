package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest

class NumberColumn<T>(
    name: String,
    typeName: String = "",
    default: T? = null,
    override val size: T? = null,
    override val primary: Boolean = false,
    private val autoIncrement: Boolean = false,
    nullable: Boolean = primary,
    alias: String? = null,
    parser: (String?) -> T
) : BaseColumn<T>(
    name,
    typeName,
    default,
    primary || nullable,
    { it.toString() },
    alias,
    parser
), PrimaryColumn, SizedColumn<T> {

    override val sql by laziest {
        "$identifier $typeName$sizedSql$primarySql$autoIncrementSql$nullableSql$defaultSql"
    }

    private val autoIncrementSql
        get() = if (autoIncrement) " AUTOINCREMENT" else ""

    override fun <C : Column<T>> copy(alias: String): C {
        TODO("Not yet implemented")
    }
}