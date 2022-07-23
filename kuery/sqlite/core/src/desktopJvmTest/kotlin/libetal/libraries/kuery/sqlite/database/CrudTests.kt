package libetal.libraries.kuery.sqlite.database

import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.statements.results.CreateResult
import libetal.libraries.kuery.core.statements.results.InsertResult
import libetal.libraries.kuery.core.statements.results.SelectResult
import libetal.libraries.kuery.sqlite.core.runBlocking
import libetal.libraries.kuery.sqlite.data.User
import libetal.libraries.kuery.sqlite.database.Database.query
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.createTableStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.dropTableStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.insertStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.selectStatement
import libetal.libraries.kuery.sqlite.database.tables.Users
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull

class CrudTests {

    @BeforeTest
    fun createTableTests() = createTableStatement query {
        assertNull(
            actual = error,
            message = """Failed to execute query: $createTableStatement 
                |Due to${error?.let { " ${it::class.qualifiedName} " } ?: ""}${error?.message ?: ""}""".trimMargin()
        )
        TAG info "Executed $createTableStatement"
    }

    @AfterTest
    fun dropTableTests() = dropTableStatement query {
        assertNull(
            actual = error,
            message =
            """Failed to delete table $dropTableStatement. 
               |Due to ${error?.let { " ${it::class.qualifiedName} " }}:${error?.message ?: ""}
            """.trimMargin()
        )
    }

    @Test
    fun selectUsers() = select {
        val user = User(
            Users.name.value,
            Users.age.value
        )
        println("$user")
    }

    @Test
    fun insertUser() = insertStatement query {
        assertNull(error, "Failed to insert due to : ${error?.message}")
        TAG info "Inserted $insertStatement"
    }

    fun select(then: SelectResult.() -> Unit) = selectStatement query {
        assertNull(
            actual = error,
            message =
            """Failed to execute $selectStatement.
               |Due to ${error?.let { it::class.simpleName ?: "Exception" }}: ${error?.message ?: ""}
            """.trimMargin()
        )
        then()
    }

    companion object {

        const val TAG = "CrudTest"

    }

}