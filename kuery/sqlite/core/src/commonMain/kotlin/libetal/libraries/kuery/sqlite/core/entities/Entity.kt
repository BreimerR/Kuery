package libetal.libraries.kuery.sqlite.core.entities

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity


abstract class Entity<EntityType, IdType, C : Column<IdType>> : Entity<EntityType>() {
    abstract val id: C
}



