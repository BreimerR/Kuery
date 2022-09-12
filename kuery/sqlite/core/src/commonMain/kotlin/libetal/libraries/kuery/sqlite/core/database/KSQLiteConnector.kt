package libetal.libraries.kuery.sqlite.core.database

interface KSQLiteConnector : libetal.libraries.kuery.core.Connector {
    companion object {
        fun resolveName(databaseName: String): Pair<String, String> {
            val splat = databaseName.split('/')

            var (path, name) = if (splat.size == 1) {
                "" to databaseName
            } else {
                databaseName.substringBeforeLast('/') to databaseName.substringAfterLast('/')
            }

            name = when (name.substringAfterLast('.')) {
                "db" -> name
                else -> "$name.sqlite"
            }

            return path to name
        }
    }
}