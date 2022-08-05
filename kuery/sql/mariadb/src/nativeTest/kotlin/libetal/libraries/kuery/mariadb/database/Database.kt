package libetal.libraries.kuery.mariadb.database

import libetal.kotlin.laziest
import libetal.libraries.kuery.mariadb.Kuery

object DatabaseConfig {

    // this can be loaded from a file or something as long as it's a lazy
    // Specifically for native platforms
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

}