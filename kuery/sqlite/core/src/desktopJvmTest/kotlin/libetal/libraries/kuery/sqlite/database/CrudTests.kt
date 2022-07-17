package libetal.libraries.kuery.sqlite.database

import libetal.libraries.kuery.core.statements.results.CreateResult
import libetal.libraries.kuery.core.statements.results.InsertResult
import libetal.libraries.kuery.sqlite.database.Database.query
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.createTableStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.insertStatement
import libetal.libraries.kuery.sqlite.database.StatementTest.Companion.selectStatement
import org.junit.Test
import kotlin.test.assertNull


class CrudTests {

    fun withinTable(then: CreateResult.() -> Unit) = createTableStatement query {

        assertNull(
            actual = error,
            message = """Failed to execute query: $createTableStatement 
                |Due to${error?.let { " ${it::class.qualifiedName} " } ?: ""}${error?.message ?: ""}""".trimMargin()
        )

        then()


    }


    @Test
    fun initial() = withinTable {
        println("Created")
    }

    fun selectUsers() = insert {
        selectStatement query {
            // assert inserted
        }
    }

    fun insert(then: InsertResult.() -> Unit) = insertStatement query {
        assertNull(error, "Failed to insert due to : ${error?.message}")
        then()
    }


    fun insertUserTest() = insert {

    }

    companion object {

        const val TAG = "CrudTest"

    }

}