import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import api.PlaceholderApi
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        var placeholders by remember { mutableStateOf<List<PlaceholderModel>?>(null) }
        var refresh by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            placeholders = PlaceholderApi.getPlaceholderModels()
        }
        LaunchedEffect(refresh){
            if (refresh){
                placeholders = PlaceholderApi.getPlaceholderModels()
            }
        }
        val readOnlyPlaceholders = placeholders
        when {
            readOnlyPlaceholders == null -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            readOnlyPlaceholders.isEmpty() -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column {
                    Text(
                        "Empty data"
                    )
                    Button(onClick = {
                        placeholders = null
                        refresh = false
                        refresh = true
                    }){
                        Text("Refresh")
                    }
                }

            }

            else -> {
                val middleIndex = (readOnlyPlaceholders.size / 2) + (readOnlyPlaceholders.size % 2)
                val horizontalItems by remember {
                    derivedStateOf { readOnlyPlaceholders.subList(0, middleIndex) }
                }
                val verticalItems by remember {
                    derivedStateOf {
                        readOnlyPlaceholders.subList(
                            middleIndex + 1,
                            readOnlyPlaceholders.lastIndex
                        )
                    }
                }

                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(horizontalItems, key = { it.id }) { item ->
                                    PlaceholderItem(
                                        modifier = Modifier
                                            .height(82.dp),
                                        text = item.title,
                                        isCompleted = item.completed
                                    )
                                }
                            }
                        }
                        items(verticalItems, key = { it.id }) { item ->
                            PlaceholderItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = item.title,
                                isCompleted = item.completed
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlaceholderItem(
    modifier: Modifier = Modifier,
    text: String,
    isCompleted: Boolean,
) {
    Row(
        modifier
            .background(
                color = if (isCompleted) Color.DarkGray else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = if (isCompleted) Color.White else Color.Black
        )
        Image(
            painter = painterResource(DrawableResource(if (isCompleted) "ic_checked.xml" else "ic_unchecked.xml")),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 4.dp),
            colorFilter = ColorFilter.tint(if (isCompleted) Color.White else Color.Black)
        )
    }
}