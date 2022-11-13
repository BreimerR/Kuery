package libetal.libraries.kuery.test.database

import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.entities.Entity

object Database : Kuery() {

    const val TAG = "Database"
    override val entities: List<Entity<*, *>>
        get() = listOf(

        )

}