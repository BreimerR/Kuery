package libetal.libraries.kuery.mariadb.database.tables

import kotlinx.datetime.LocalDate
import libetal.libraries.kuery.mariadb.database.Database.date
import libetal.libraries.kuery.mariadb.database.Database.string
import libetal.libraries.kuery.mariadb.entities.Entity


data class User(
    val name: String,
    val dateOfBirth: LocalDate
)

object Users : Entity<User>() {

    val name = string("name")

    val dob = date("dob")

    override fun getEntityTableName(): String = "users"

}