package libetal.libraries.kuery.annotations.databases

@Target(AnnotationTarget.CLASS)
annotation class PostgreSQL(
    val name: String = ""
)