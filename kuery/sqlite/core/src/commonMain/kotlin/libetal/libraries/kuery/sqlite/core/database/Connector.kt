package libetal.libraries.kuery.sqlite.core.database

import libetal.libraries.kuery.sqlite.core.Kuery

expect class Connector : libetal.libraries.kuery.core.Connector {

    override val database: String

    operator fun Kuery.invoke()

    companion object {
        operator fun invoke(): libetal.libraries.kuery.core.Connector
    }

}


