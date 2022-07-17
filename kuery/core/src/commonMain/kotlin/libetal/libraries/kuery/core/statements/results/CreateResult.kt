package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.entities.Entity

class CreateResult(
    val name: String,
    val type: Entity.Type,
    // val columns: List<Int>
    error: Exception? = null
) : Result(error)