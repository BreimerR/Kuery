package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name

class Drop(entity: Entity<*>, var safe: Boolean = false, val type: Entity.Type = Entity.Type.TABLE) : Statement() {

    override val sql by laziest {
        """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`""".trimMargin()
    }

    override val boundSql by laziest {
        """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`""".trimMargin()
    }

    infix fun IF(existence: Existence) = this.apply {
        safe = existence.state
    }

}

