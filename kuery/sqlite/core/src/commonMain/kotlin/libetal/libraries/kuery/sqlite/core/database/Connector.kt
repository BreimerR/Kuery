package libetal.libraries.kuery.sqlite.core.database

expect class Connector : libetal.libraries.kuery.core.Connector {

    override val database: String

    companion object {
        operator fun invoke(): libetal.libraries.kuery.core.Connector
    }

}


