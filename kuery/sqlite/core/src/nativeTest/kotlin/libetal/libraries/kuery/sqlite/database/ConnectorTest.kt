package libetal.libraries.kuery.sqlite.database

import kotlin.test.Test

class ConnectorTest {
    @Test
    fun connection(){
        val connector = Connector("users",1)
        connector.connection
    }
}