package libetal.libraries.kuery.test.models

import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.sqlite.core.entities.IdEntity
import libetal.libraries.kuery.test.data.User
import libetal.libraries.kuery.test.database.Database.int
import libetal.libraries.kuery.test.database.Database.query
import libetal.libraries.kuery.test.database.Database.text

object Users : IdEntity<User, Int>() {

    override val id = int("id", primary = true)
    val age = int("age")
    val name = text("name")

    override fun toString(): String = "users"

    override suspend fun onCreate() {
        INSERT INTO Users(name, age) VALUES {
            row("Breimer", 12)
        } query {

        }
    }

}