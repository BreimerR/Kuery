package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector

object Database : Kuery() {

    override fun init(): CoreConnector = Connector("", 1)

    override fun onCreate() {
        CREATE TABLE Users
    }

}