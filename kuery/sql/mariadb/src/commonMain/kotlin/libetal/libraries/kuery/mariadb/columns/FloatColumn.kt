package libetal.libraries.kuery.mariadb.columns

fun toFloat(string: String?) = string?.toFloat() ?: throw RuntimeException("Received null expected float string")