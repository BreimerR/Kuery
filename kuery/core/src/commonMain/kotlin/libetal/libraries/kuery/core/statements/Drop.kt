package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name

class Drop<Class, E : Entity<Class>>(entity: E, val type: Entity.Type = Entity.Type.TABLE, private val safe: Boolean = false) :
    Statement<Class, E>(
        """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`""".trimMargin(),
        entity
    ) {

    infix fun IF(existence: Existence) = Drop(entity, type, existence.state)

}

