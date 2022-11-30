package libetal.libraries.kuery.plugin.common.objects

object Annotations {
    // ibetal.libraries.kuery.annotations.common.entities.Table
    private const val PACKAGE_NAME = "libetal.libraries.kuery.annotations"
    private const val DATABASES_PACKAGE_NAME = "$PACKAGE_NAME.databases"
    const val SQLITE = "$DATABASES_PACKAGE_NAME.SQLite"
    const val POSTGRE_SQL = "$DATABASES_PACKAGE_NAME.PostgreSQL"
    const val MYSQL = "$DATABASES_PACKAGE_NAME.MySQL"
    const val KUERY = "libetal.libraries.kuery.annotations.common.entities.Kuery"

    private const val ENTITY_PACKAGE_NAME = "$PACKAGE_NAME.entities"
    private const val PROPERTY_PACKAGE_NAME = "$PACKAGE_NAME.properties"

    const val TABLE = "$ENTITY_PACKAGE_NAME.Table"
    const val VIEW = "$ENTITY_PACKAGE_NAME.View"
    const val COLUMN = "$PROPERTY_PACKAGE_NAME.Column"
    const val RETRIEVED = "$PROPERTY_PACKAGE_NAME.Retrieved"
    const val PRIMARY_KEY = "$PROPERTY_PACKAGE_NAME.PrimaryKey"
    const val FOREIGN_KEY = "$PROPERTY_PACKAGE_NAME.ForeignKey"

}