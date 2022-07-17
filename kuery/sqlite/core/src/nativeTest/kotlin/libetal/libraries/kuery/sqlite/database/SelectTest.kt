package libetal.libraries.kuery.sqlite.database

import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.expressions.extensions.AND
import libetal.libraries.kuery.core.expressions.extensions.OR
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.core.statements.SELECT
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectTest {
    @Test
    fun selectSimple() {

        val statement = SELECT * Users WHERE (Users.name equals "Breimer")

        TAG info statement.toString()

        assertEquals("SELECT * FROM `users` WHERE `users.name` = 'Breimer'", statement.toString())
    }

    @Test
    fun selectBoundSimple() {

        val statement = SELECT * Users WHERE (Users.name equals "Breimer")

        TAG info statement.toString()

        assertEquals("SELECT * FROM `users` WHERE `users.name` = ?", statement.boundSql)
    }

    @Test
    fun selectSimpleAnd() {

        val statement = SELECT ALL Users WHERE (Users.name equals "Breimer" AND {
            Users.isPuppy equivalent true
        })

        assertEquals("SELECT * FROM `users` WHERE `users.name` == 'Breimer' AND `users.isPuppy` == 1", statement.toString())

    }

    @Test
    fun createSimple() {


        TAG info Users.name.createSql
        TAG info Users.isPuppy.createSql
        TAG info Users.blobby.createSql

        val statement = with(Database) {
            CREATE TABLE Users
        }
        assertEquals("CREATE TABLE `users` ( )", statement.toString())

    }

    companion object {
        @Test
        fun selectSimpleOr() {
            val statement = SELECT * Users WHERE (Users.name equals "Breimer" OR {
                Users.isPuppy equivalent true
            })

            TAG info statement.toString()

            assertEquals("SELECT * FROM `users` WHERE `users.name` == 'Breimer' OR `users.isPuppy` == 1", statement.toString())

        }

        private const val TAG = "SelectTest"

    }

}