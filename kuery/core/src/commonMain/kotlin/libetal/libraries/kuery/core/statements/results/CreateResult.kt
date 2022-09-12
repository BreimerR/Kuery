package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.entities.Entity

class CreateResult(
    val name: String,
    val type: Entity.Type,
    override val error: Exception? = null
) : Result