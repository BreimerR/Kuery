package libetal.libraries.kuery.sqlite.core.entities

import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity

/**
 * All Tables in sqlite have an index
 * column to improve performance
 **/
abstract class Entity<EntityType, IdType, C : EntityColumn<IdType>> : Entity<EntityType>() {
    abstract val id: C
}



