package libetal.libraries.kuery.sqlite.database

import libetal.kotlin.debug.info
import libetal.libraries.kuery.sqlite.coroutines.runBlocking
import libetal.libraries.kuery.sqlite.data.User
import libetal.libraries.kuery.sqlite.database.Database.query
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.createTableStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.dropTableStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.insertStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.selectStatement
import libetal.libraries.kuery.sqlite.database.tables.Users
import kotlin.reflect.KFunction0
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull

class CrudTests {

    @Test
    fun selectUsersTest() = ::insertUsers then ::selectUsers

    @Test
    fun insertUsersTest() = insertUsers()

    private fun insertUsers() = runBlocking {
        insertStatement query {
            assertNull(error, "Failed to insert due to : ${error?.message}")
            TAG info "Inserted: $insertStatement"
        }
    }

    private fun selectUsers() = runBlocking {
        selectStatement query {
            val user = User(
                Users.name.value.toString(),
                Users.age.value
            )
            println("$user")
        }
    }

    @BeforeTest
    fun createTableTests() = runBlocking {
        createTableStatement query {
            assertNull(
                actual = error,
                message = """Failed to execute query: $createTableStatement 
                |Due to${error?.let { " ${it::class.qualifiedName} " } ?: ""}${error?.message ?: ""}""".trimMargin()
            )
            TAG info "Executed $createTableStatement"
        }
    }

    @AfterTest
    fun dropTableTests() = runBlocking {
        dropTableStatement query {
            assertNull(
                actual = error,
                message =
                """Failed to delete table $dropTableStatement. 
               |Due to ${error?.let { " ${it::class.qualifiedName} " }}:${error?.message ?: ""}
            """.trimMargin()
            )
            TAG info "Executed: $dropTableStatement"
        }
    }

    private infix fun KFunction0<Unit>.then(then: KFunction0<Unit>) {
        this()
        then()
    }

    companion object {

        const val TAG = "CrudTest"

    }

}