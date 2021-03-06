package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.database.Connector
import libetal.libraries.kuery.sqlite.database.tables.Users

object Database : Kuery() {

    override fun init(): Connector = Connector("src/desktopJvmTest/resources/desktop", 1)

    override fun onCreate() {
        CREATE TABLE Users
    }

}