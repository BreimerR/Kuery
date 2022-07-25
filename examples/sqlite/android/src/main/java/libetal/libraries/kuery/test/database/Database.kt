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

    override fun onCreate() {
        Log.d(TAG, "Inserting users")
        insertDefaultUserStatement query {
            assert(error == null) {
                "Failed to execute $ $error"
            }
        }
        Log.d(TAG, "Inserted users")
    }

    const val TAG = "Database"

    val insertDefaultUserStatement by laziest {
        INSERT INTO Users(Users.name, Users.age) VALUES {
            row("Breimer", 12)
        }
    }

}