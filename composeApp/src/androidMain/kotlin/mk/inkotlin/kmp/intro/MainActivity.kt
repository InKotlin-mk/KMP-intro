package mk.inkotlin.kmp.intro

import App
import PlaceHoldersScreen
import PlaceholderModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}


@Composable
@Preview
fun PlaceHoldersScreenAndroidPreview() {
    MaterialTheme {
        Surface {
            PlaceHoldersScreen(
                readOnlyPlaceholders = remember {
                    listOf(
                        PlaceholderModel(
                            userId = 6579,
                            id = 3622,
                            title = "Charlita",
                            completed = true
                        ),

                        PlaceholderModel(
                            userId = 3370,
                            id = 1921,
                            title = "Kaili",
                            completed = true
                        ),

                        PlaceholderModel(
                            userId = 3065,
                            id = 4137,
                            title = "Nereyda",
                            completed = true
                        ),

                        PlaceholderModel(
                            userId = 1324,
                            id = 3777,
                            title = "Dwayne",
                            completed = false
                        ),

                        PlaceholderModel(
                            userId = 4994,
                            id = 8067,
                            title = "Jeanpierre",
                            completed = false
                        ),


                        )
                },
                refreshData = {}
            )
        }
    }
}