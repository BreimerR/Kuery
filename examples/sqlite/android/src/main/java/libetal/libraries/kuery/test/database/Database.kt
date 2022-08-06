package libetal.libraries.kuery.test.database

import libetal.libraries.kuery.sqlite.core.CoreKuery
import libetal.libraries.kuery.sqlite.core.entities.Entity

object Database : CoreKuery() {

    const val TAG = "Database"
    override val entities: List<Entity<*, *>>
        get() = TODO("Not yet implemented")

}