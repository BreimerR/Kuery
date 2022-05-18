package libetal.libraries.kuery.sqlite.example.database.models

import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.core.statements.extensions.INTO
import libetal.libraries.kuery.core.statements.extensions.VALUES
import libetal.libraries.kuery.core.statements.extensions.WHERE
import kotlin.test.Test
import kotlin.test.assertEquals

class UsersTest {

    @Test
    fun testSelectAll() {

        val statement = SELECT * Users WHERE true

        assertEquals("SELECT * FROM `users` WHERE true", statement.toString())

        TAG info statement.toString()

    }

    @Test
    fun testSelectWhere() {
        val statement = SELECT * Users WHERE (Users.name equals "Breimer")

        assertEquals("SELECT * FROM `users` WHERE `users.name` == 'Breimer'", statement.toString())

    }

    @Test
    fun testInsertSimple() {

        val statement = INSERT INTO Users VALUES {
            it.name to "Breimer"
        }

        assertEquals("INSERT INTO `users` (`users.name`) VALUES ('Breimer')", statement.toString())

    }

    companion object {
        private const val TAG = "UsersTest"
    }

}