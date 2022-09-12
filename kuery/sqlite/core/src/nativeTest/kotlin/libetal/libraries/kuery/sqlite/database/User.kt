package libetal.libraries.kuery.sqlite.database


import libetal.libraries.kuery.sqlite.core.entities.IdEntity
import libetal.libraries.kuery.sqlite.database.Database.blob
import libetal.libraries.kuery.sqlite.database.Database.boolean
import libetal.libraries.kuery.sqlite.database.Database.long
import libetal.libraries.kuery.sqlite.database.Database.real
import libetal.libraries.kuery.sqlite.database.Database.text


class User

object Users : IdEntity<User, Long>() {

    override val id = long("id")

    val name = text("name")

    val blobby = blob("blobby")

    val keepingItReal = real("keepingItReal")

    val isPuppy = boolean("isPuppy")
    override fun toString(): String = "users"

}