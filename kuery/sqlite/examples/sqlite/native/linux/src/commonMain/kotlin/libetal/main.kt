package libetal

import libetal.database.models.Users
import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.core.statements.extensions.WHERE

private const val TAG = "Main";

fun main(vararg arguments: String) {
    val statement = SELECT * Users WHERE (Users.id equals 12L)

    TAG info "statement.toString()"

}
