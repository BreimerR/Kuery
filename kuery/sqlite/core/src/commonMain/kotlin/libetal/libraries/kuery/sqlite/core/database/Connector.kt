package libetal.libraries.kuery.sqlite.core.database

import libetal.libraries.kuery.sqlite.core.Kuery

expect class Connector : KSQLiteConnector {
    operator fun Kuery.invoke()

    companion object {
        operator fun invoke(): libetal.libraries.kuery.core.Connector
    }

}


