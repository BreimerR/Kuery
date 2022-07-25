package libetal.libraries.kuery.test.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import libetal.libraries.kuery.core.statements.SELECT
import libetal.libraries.kuery.test.data.User
import libetal.libraries.kuery.test.database.Database.query
import libetal.libraries.kuery.test.models.Users
import libetal.libraries.kuery.test.ui.theme.KueryTestTheme

class MainActivity : ComponentActivity() {

    val users = mutableStateListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectUser()
        setContent {
            KueryTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(remember { users })
                }
            }
        }
    }

    fun selectUser() {
        SELECT * Users WHERE true query {
            val user = User(
                Users.name.value,
                Users.age.value

            )

            Log.d(TAG, "Selected $user Error = $error")
            users += user
        }
        Log.d(TAG, "Selected users")
    }

    companion object {
        const val TAG = "MainActivity"
    }

}

@Composable
fun Greeting(users: List<User>) {
    LazyColumn {
        items(users) {
            Text(text = it.name)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = it.age.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KueryTestTheme {
        Greeting(
            listOf(
                User("Breimer", 20)
            )
        )
    }
}