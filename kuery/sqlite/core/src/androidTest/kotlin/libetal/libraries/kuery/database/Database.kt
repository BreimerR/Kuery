package libetal.libraries.kuery.database

import libetal.libraries.kuery.sqlite.core.CoreKuery
import libetal.libraries.kuery.sqlite.core.entities.Entity

object Database : CoreKuery() {
    override val entities: List<Entity<*, *>>
        get() = TODO("Not yet implemented")
}