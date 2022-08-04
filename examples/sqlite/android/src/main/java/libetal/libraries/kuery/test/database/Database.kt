package libetal.libraries.kuery.test.database

import android.util.Log
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.sqlite.core.Kuery
import libetal.libraries.kuery.sqlite.core.database.Connector
import libetal.libraries.kuery.test.models.Users

object Database : Kuery() {

    const val TAG = "Database"

}