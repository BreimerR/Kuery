package libetal.libraries.kuery.sqlite.example.database.models

import libetal.libraries.kuery.annotations.common.entities.Table
import libetal.libraries.kuery.entities.IdEntity
import libetal.libraries.kuery.sqlite.example.data.User
import libetal.libraries.kuery.sqlite.example.database.Database.long
import libetal.libraries.kuery.sqlite.example.database.Database.string

@Table("users")
object Users : IdEntity<User>() {

    override val id by long()

    val name by string()

    override fun getEntityTableName(): String = "users"

}