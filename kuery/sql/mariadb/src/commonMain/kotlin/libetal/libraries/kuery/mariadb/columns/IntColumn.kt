package libetal.libraries.kuery.mariadb.columns

fun toInt(string: String?) = string?.toInt() ?: throw RuntimeException("Received null expected Integer in string")

