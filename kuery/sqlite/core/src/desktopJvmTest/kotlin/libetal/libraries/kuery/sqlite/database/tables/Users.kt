package libetal.libraries.kuery.sqlite.database.tables

import libetal.libraries.kuery.sqlite.core.entities.IdEntity
import libetal.libraries.kuery.sqlite.data.User
import libetal.libraries.kuery.sqlite.database.Database.int
import libetal.libraries.kuery.sqlite.database.Database.long
import libetal.libraries.kuery.sqlite.database.Database.text

object Users : IdEntity<User>() {
    override val id = long("user")
    val name = text("name")
    val age = int("age")
    override fun getEntityName(): String = "users"
}