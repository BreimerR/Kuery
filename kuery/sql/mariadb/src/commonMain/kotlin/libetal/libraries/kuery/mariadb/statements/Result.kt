package libetal.libraries.kuery.mariadb.statements

import libetal.libraries.kuery.core.entities.Entity


class SelectResult(
    val table: String,
    val columnName: String,
    val value: String?, // null if row is nullable
    error: RuntimeException? = null
) : libetal.libraries.kuery.core.statements.Result(error)

class DeleteResult(
    val table: String,
    // val columns: List<Int>
    error: RuntimeException? = null
) : libetal.libraries.kuery.core.statements.Result(error)

class CreateResult(
    val name: String,
    val type: Entity.Type,
    // val columns: List<Int>
    error: RuntimeException? = null
) : libetal.libraries.kuery.core.statements.Result(error)

class InsertResult(
    val into: String,
    // val columns: List<Int>
    error: RuntimeException? = null
) : libetal.libraries.kuery.core.statements.Result(error)