package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.entities.Entity

object Database : Kuery() {
    override val entities: List<Entity<*, *>>
        get() = TODO("Not yet implemented")
}