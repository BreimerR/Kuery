package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.tableEntities

class Create<Class, E : Entity<Class>>(
    entity: E,
    val database: Kuery<*>,
    val type: Entity.Type = Entity.Type.TABLE,
    safe: Boolean = true
) : Statement<Class, E>(
    """|CREATE $type ${if (safe) "IF NOT EXISTS " else ""}`${entity.name}` (
       |    ${tableEntities[entity]?.joinToString(",\n    ") { it.createSql }}
       |);
    """.trimMargin(),
    entity
) {
    override val boundSql: String
        get() = sql
}

class FinalCreate<Class, E : Entity<Class>>(
    entity: E,
    val database: Kuery<*>,
    val type: Entity.Type = Entity.Type.TABLE,
    safe: Boolean = true
) : FinalStatement() {

    override val sql: String by laziest {
        """|CREATE $type ${if (safe) "IF NOT EXISTS " else ""}`${entity.name}` (
           |    ${tableEntities[entity]?.joinToString(",\n    ") { it.createSql }}
           |);
        """.trimMargin()
    }

    override val boundSql: String
        get() = sql
}


