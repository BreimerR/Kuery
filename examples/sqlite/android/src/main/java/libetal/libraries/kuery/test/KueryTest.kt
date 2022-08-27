package libetal.libraries.kuery.test

import android.app.Application
import libetal.libraries.kuery.sqlite.core.database.Connector

class KueryTest : Application() {
    override fun onCreate() {
        super.onCreate()
        Connector(applicationContext, "test", 1)
    }

}