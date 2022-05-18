package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.KSQLite
import libetal.libraries.kuery.sqlite.core.database.Connector as CoreConnector

object Database : KSQLite() {

    override fun init(): CoreConnector = Connector("", 1)

    override fun <T, E : Entity<T>> execute(statement: Statement<T, E>) {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        CREATE TABLE Users
    }

}