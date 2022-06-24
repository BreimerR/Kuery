package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity

class BooleanColumn : Column<Boolean> {

    constructor(
        name: String,
        default: Boolean? = null
    ) : super(name, "`$name` BOOLEAN", default, ::toBoolean)

    constructor(
        name: String,
    ) : super(name, "`$name` BOOLEAN", false, ::toBoolean)


    override val createSql: String by laziest {
        baseSql + (default?.let { " DEFAULT $it" } ?: "")
    }

    override fun Boolean.defaultSql(): String = this.toString()

}


fun toBoolean(string: String?) =
    string?.toBoolean() ?: throw NullPointerException("Received null value from db for a non null field")