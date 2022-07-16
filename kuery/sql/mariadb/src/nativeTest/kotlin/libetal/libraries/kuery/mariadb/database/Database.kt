package libetal.libraries.kuery.mariadb.database

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.mariadb.Kuery
import libetal.libraries.kuery.mariadb.database.tables.Users

object DatabaseConfig {

    private val configSource by laziest {
        mapOf<String, Any>(
            "database" to "test",
            "user" to "test",
            "password" to "test_password",
            "host" to "localhost",
            "port" to 3306u,
        )
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(config: String): T = configSource[config] as T

}

object Database : Kuery(
    DatabaseConfig["database"],
    DatabaseConfig["user"],
    DatabaseConfig["password"],
    DatabaseConfig["host"],
    DatabaseConfig["port"]
) {

    override fun onCreate() {
        CREATE TABLE Users
    }
}