package libetal.database.models

import libetal.libraries.kuery.KSQLite
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Statement

object Database : KSQLite() {
    override fun <T, E : Entity<T>> execute(statement: Statement<T, E>) {
        TODO("Not yet implemented")
    }
}