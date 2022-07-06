package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name

class Drop<Class, E : Entity<Class>>(entity: E, val type: Entity.Type = Entity.Type.TABLE, private val safe: Boolean = false) :
    Statement<Class, E>(
        """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`""".trimMargin(),
        entity
    ) {

    infix fun IF(existence: Existence) = Drop(entity, type, existence.state)

}

class FinalDrop(entity: Entity<*>, var safe: Boolean, val type: Entity.Type = Entity.Type.TABLE) : FinalStatement() {

    override val sql by laziest {
        """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`""".trimMargin()
    }

    override val boundSql by laziest {
        """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`""".trimMargin()
    }
}

