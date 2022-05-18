package libetal.libraries.kuery.mariadb.database

import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.mariadb.Kuery
import libetal.libraries.kuery.mariadb.database.tables.Users

object Database : Kuery() {

    const val host = "localhost"
    const val user = "test"
    const val password = "test_password"
    const val database = "test"
    const val port = 3306u

    override fun onCreate() {
        CREATE TABLE Users
    }
}