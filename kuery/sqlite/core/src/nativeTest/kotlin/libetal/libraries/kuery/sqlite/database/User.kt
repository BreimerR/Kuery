package libetal.libraries.kuery.sqlite.database


import libetal.libraries.kuery.sqlite.core.entities.IdEntity
import libetal.libraries.kuery.sqlite.database.Database.blob
import libetal.libraries.kuery.sqlite.database.Database.boolean
import libetal.libraries.kuery.sqlite.database.Database.long
import libetal.libraries.kuery.sqlite.database.Database.real
import libetal.libraries.kuery.sqlite.database.Database.text


class User

object Users : IdEntity<User>() {

    override val id = long("id")

    val name = text("id")

    val blobby = blob("id")

    val keepingItReal = real("id")

    val isPuppy = boolean("id")

    override fun getEntityName(): String = "users"

    override fun toString(): String  = "users"

}