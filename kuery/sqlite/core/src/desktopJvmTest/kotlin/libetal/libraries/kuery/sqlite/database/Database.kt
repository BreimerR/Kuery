package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.database.Connector
import libetal.libraries.kuery.sqlite.database.tables.Users

object Database : Kuery() {

    override fun init(): Connector = Connector("", 1)

    override fun <T, E : Entity<T>> execute(statement: Statement<T, E>) {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        CREATE TABLE Users
    }

}