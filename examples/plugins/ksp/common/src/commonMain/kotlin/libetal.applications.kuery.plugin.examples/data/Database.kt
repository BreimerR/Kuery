package libetal.applications.kuery.plugin.examples.data

import libetal.applications.kuery.entities.UsersEntity
import libetal.applications.kuery.plugin.examples.data.Database.long
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.entities.Entity
import libetal.libraries.kuery.sqlite.core.entities.IdEntity


object Database : Kuery() {

    override val entities: List<Entity<*, *>>
        get() = listOf(
            UsersEntity
        )

    init {
        init()
    }

}