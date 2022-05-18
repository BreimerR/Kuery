package libetal.libraries.kuery.sqlite.examples

import android.app.Application
import libetal.kotlin.debug.info
import libetal.libraries.kuery.database.Connector

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Connector(applicationContext, "kuery", 1)
        TAG info "Initialized database"
    }

    companion object {
        private const val TAG = "Kuery"
    }
}