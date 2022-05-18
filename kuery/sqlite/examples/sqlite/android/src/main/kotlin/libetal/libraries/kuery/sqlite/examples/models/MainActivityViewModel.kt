package libetal.libraries.kuery.sqlite.examples.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import libetal.libraries.kuery.sqlite.example.data.Account
import libetal.libraries.kuery.sqlite.example.data.User

class MainActivityViewModel : ViewModel() {
    val user = mutableStateOf<User?>(null)
    val userAccount = mutableStateOf<Account?>(null)

    fun onCreate() {


    }
}