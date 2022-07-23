package libetal.libraries.kuery.core.columns

interface PrimaryColumn {

    val primary: Boolean

    val primarySql
        get() = if (primary) " PRIMARY KEY" else ""

}