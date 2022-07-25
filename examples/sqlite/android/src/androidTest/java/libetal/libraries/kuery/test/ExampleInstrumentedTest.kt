package libetal.libraries.kuery.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.sqlite.core.database.Connector
import libetal.libraries.kuery.test.activities.MainActivity
import libetal.libraries.kuery.test.data.User
import libetal.libraries.kuery.test.database.Database.query
import libetal.libraries.kuery.test.models.Users

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("libetal.libraries.kuery.test", appContext.packageName)
    }

    @Test
    fun testUserQuery() {
        activityRule.scenario.onActivity {

            Connector(it, "test", 1)

            SELECT * Users WHERE true query {
                assertNull("Failed to fetch users $error", error)
                val user = User(
                    Users.name.value,
                    Users.age.value
                )

                println(user)

            }


        }
    }

    companion object {
        const val TAG = "ExampleInstrumentedTest"
    }
}