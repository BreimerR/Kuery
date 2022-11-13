package libetal.libraries.kuery.sqlite.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import libetal.kotlin.coroutines.IO
import libetal.kotlin.log.info
import libetal.libraries.kuery.core.columns.extensions.equals
import libetal.libraries.kuery.core.expressions.extensions.AND
import libetal.libraries.kuery.core.expressions.extensions.OR
import libetal.libraries.kuery.core.statements.CREATE
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.sqlite.core.database.Connector
import libetal.libraries.kuery.sqlite.database.Database.query
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectTest {

    @BeforeTest
    fun initDatabase() {
        Connector("", "name")
    }

    @Test
    fun selectSimple() {

        val statement = SELECT * Users WHERE (Users.name equals "Breimer")

        assertEquals(
            "SELECT id, name, blobby, keepingItReal, isPuppy FROM `users` WHERE name = 'Breimer'",
            statement.toString()
        )
    }

    @Test
    fun selectBoundSimple() {

        val statement = SELECT * Users WHERE (Users.name equals "Breimer")

        assertEquals("SELECT id, name, blobby, keepingItReal, isPuppy FROM `users` WHERE name = ?", statement.boundSql)
    }

    @Test
    fun selectSimpleAnd() {

        val statement = SELECT ALL Users WHERE (Users.name equals "Breimer" AND {
            Users.isPuppy equivalent true
        })

        assertEquals(
            "SELECT * FROM `users` WHERE `users.name` == 'Breimer' AND `users.isPuppy` == 1",
            statement.toString()
        )

    }

    @Test
    fun createSimple() {


        TAG info Users.name.sql
        TAG info Users.isPuppy.sql
        TAG info Users.blobby.sql

        val statement = with(Database) {
            CREATE TABLE Users
        }

        assertEquals(
            """CREATE TABLE users (
                |    id NUMERIC NOT NULL,
                |    name TEXT(55) NOT NULL,
                |    blobby `blobby` BLOB NOT NULL,
                |    keepingItReal REAL NOT NULL DEFAULT 'false',
                |    isPuppy NUMERIC NOT NULL
                |);""".trimMargin(), statement.toString()
        )

    }

    @Test
    fun selectSimpleOr() {
        val statement = SELECT * Users WHERE (Users.name equals "Breimer" OR {
            Users.isPuppy equivalent true
        })

        TAG info statement.toString()

        assertEquals(
            "SELECT * FROM `users` WHERE `users.name` == 'Breimer' OR `users.isPuppy` == 1",
            statement.toString()
        )

    }

    @Test
    fun resolveTest() {

        val name = "databaseName"
        val lastName = name.substringAfterLast("/")
        val beforeName = name.substringBeforeLast("/")

        assertEquals(name, lastName)
        assertEquals("", beforeName)

    }

    data class User(val name: String) {
        var id: Long = 0
    }

    @Test
    fun mappingTest() {

        val statement = SELECT * Users WHERE true

        MainScope().launch(Dispatchers.IO) {
            statement query {
                val user = User(
                    Users.name.stringValue,
                ).also {
                    it.id = Users.id.value
                }

                println(user)
            }
        }

    }

    companion object {

        private const val TAG = "SelectTest"

    }

}