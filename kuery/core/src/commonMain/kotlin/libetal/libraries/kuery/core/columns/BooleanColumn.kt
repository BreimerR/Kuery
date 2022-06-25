package libetal.libraries.kuery.core.columns

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity

fun toBoolean(string: String?) =
    string?.toBoolean() ?: throw NullPointerException("Received null value from db for a non null field")