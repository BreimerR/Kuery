package libetal.libraries.kuery.core

import libetal.libraries.kuery.core.columns.BooleanColumn
import libetal.libraries.kuery.core.entities.Entity

/**
 * Every extend this class to get
 * an instance of the required
 * database
 **/
abstract class KSQL : Kuery<Entity<*>>() {
    fun <ET, E : Entity<ET>> E.boolean(name: String = "", default: Boolean? = null) = registerColumn(name) {
        BooleanColumn(it, default)
    }

}