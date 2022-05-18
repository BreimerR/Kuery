package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.tableEntities

open class Create<Class, E : Entity<Class>>(entity: E, val database: Kuery<*>, type: Entity.Type = Entity.Type.TABLE) :
    CreateStatement<Class, E>(
        """|CREATE $type `${entity.name}` (
           |    ${database.tableEntities[entity]?.joinToString(",\n    ") { it.createSql }}
           |);
        """.trimMargin(),
        entity
    ) {

}