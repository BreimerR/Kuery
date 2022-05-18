package libetal.libraries.kuery.sqlite.database


import libetal.libraries.kuery.sqlite.core.entities.IdEntity
import libetal.libraries.kuery.sqlite.database.Database.numeric
import libetal.libraries.kuery.sqlite.database.Database.text
import libetal.libraries.kuery.sqlite.database.Database.blob
import libetal.libraries.kuery.sqlite.database.Database.real
import libetal.libraries.kuery.sqlite.database.Database.boolean


class User

object Users : IdEntity<User>() {

    override val id = numeric<Long>("id")

    val name = text("id")

    val blobby = blob("id")

    val keepingItReal = real("id")

    val isPuppy = boolean("id")

    override fun getEntityTableName(): String = "users"

}