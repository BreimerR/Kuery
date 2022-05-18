package libetal.database.models

import libetal.data.User
import libetal.database.models.Database.numeric
import libetal.libraries.kuery.entities.IdEntity

object Users : IdEntity<User>() {

    override val id by numeric<Long>()

    override fun getEntityTableName(): String = "users"

}


