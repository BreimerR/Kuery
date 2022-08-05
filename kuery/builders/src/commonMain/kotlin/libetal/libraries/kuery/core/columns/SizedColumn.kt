package libetal.libraries.kuery.core.columns

interface SizedColumn<N> {

    val size: N?

    val sizedSql
        get() = size?.let { "($it)" } ?: ""

}