package libetal.libraries.kuery.sqlite.examples;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import libetal.libraries.kuery.sqlite.examples.models.MainActivityViewModel

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Kuery()
            }
        }
    }

}

@Preview
@Composable
fun Kuery() {
    Column {
        Text(text = "Hello World")
    }

}
