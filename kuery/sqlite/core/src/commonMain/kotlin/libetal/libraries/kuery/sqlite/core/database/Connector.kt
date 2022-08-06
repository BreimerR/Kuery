package libetal.libraries.kuery.sqlite.core.database

import libetal.libraries.kuery.sqlite.core.CoreKuery

expect class Connector : libetal.libraries.kuery.core.Connector {

    override val database: String

    operator fun CoreKuery.invoke()

    companion object {
        operator fun invoke(): libetal.libraries.kuery.core.Connector
    }

}


