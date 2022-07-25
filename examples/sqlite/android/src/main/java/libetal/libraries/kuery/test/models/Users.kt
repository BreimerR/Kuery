package libetal.libraries.kuery.test.models

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.sqlite.core.entities.IdEntity
import libetal.libraries.kuery.test.data.User
import libetal.libraries.kuery.test.database.Database.int
import libetal.libraries.kuery.test.database.Database.text

object Users : IdEntity<User, Int>() {

    override val id = int("id", primary = true)

    val age = int("age")
    val name = text("name")

    override fun getEntityName(): String = "users"

    override fun toString(): String = "users"

}