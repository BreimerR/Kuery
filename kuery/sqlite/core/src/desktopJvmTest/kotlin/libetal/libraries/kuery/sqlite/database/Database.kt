package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.sqlite.database.tables.Users

object Database : Kuery() {
    override fun onCreate() {
        CREATE TABLE Users
    }

}