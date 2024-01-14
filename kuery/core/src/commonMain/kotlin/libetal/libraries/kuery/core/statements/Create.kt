package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.results.CreateResult
import libetal.libraries.kuery.core.tableEntities

class Create<Class, E : Entity<Class>>(
    val entity: E,
    val type: Entity.Type = Entity.Type.TABLE,
    var safe: Boolean = false
) : Statement<CreateResult>() {

    val entitiesString by laziest {
        tableEntities[entity]?.joinToString(",\n    ") { it.sql }
    }

    override val sql: String
        get() = """|CREATE $type ${if (safe) "IF NOT EXISTS " else ""}${entity.name} (
                   |    $entitiesString
                   |);
                """.trimMargin()

    infix fun IF(existence: Existence) = this.apply {
        safe = existence.state
    }

}
