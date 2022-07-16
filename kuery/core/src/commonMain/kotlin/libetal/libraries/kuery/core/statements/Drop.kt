package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.results.DropResult

class Drop(val entity: Entity<*>, var safe: Boolean = false, val type: Entity.Type = Entity.Type.TABLE) : Statement<DropResult>() {

    override val sql
        get() = """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`"""

    override val boundSql
        get() = """DROP $type ${if (safe) "IF EXISTS " else ""}`${entity.name}`"""

    infix fun IF(existence: Existence) = this.apply {
        safe = existence.state
    }

}

