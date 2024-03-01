import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "InKotlin_mk_kmp_intro") {
        App()
    }
}

@Composable
@Preview
fun PlaceHoldersScreenDesktopPreview() {
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