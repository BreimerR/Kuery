package libetal.libraries.kuery.plugin.sqlite.google

import com.google.devtools.ksp.processing.KSPLogger

data class EntityClass(val logger: KSPLogger, val columns: MutableList<String> = mutableListOf()) {

    var currentIndent = "    "

    val propertiesString: String
        get() = columns.joinToString("\n"){"$currentIndent$it"}

    var primaryKeyType: String? = null
        get() {
            if (field == null)
                logger.error("Entities need to have a primary key in SQLite")

            return field
        }

    fun add(propertyString: String) {
        columns.add(propertyString)
    }

}