package com.example.demoproject

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LazyListsViewScreen() {
    //Now below approach we render list but as soon as we open it loads. It's not lazy loading.

    /*
    //By default, Column is not scrollable. We have to add scrollState.
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(20.dp)
            .border(5.dp, Color.Magenta)
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (i in 1..50) {
            Text(
                "I am Item $i",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                style = TextStyle(Color.Red, fontSize = 25.sp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
     */

    // Try using LazyColumn approach. Which is effective here whenever we scroll then only that
    // data would be loaded. Which is effective.

    //To get context we have to do like below.
    val context = LocalContext.current

    LazyColumn {
        //There are two ways we can load. items and itemsIndexed. From ItemIndexed we can get index and string.
        items(25) {
            Card(modifier = Modifier.padding(2.dp)) {
                Text(
                    "Item $it",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable {
                            Toast.makeText(context, "Clicked on Item $it", Toast.LENGTH_SHORT)
                                .show()
                        },
                    style = TextStyle(
                        Color.Blue,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 25.sp
                    ),
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center,
                )
            }
        }

        //Here inside itemsIndexed used LazyRow as well. So inSize lazyColumn ...LazyRow can be used and vice versa.
        itemsIndexed(
            listOf("Hi", "This", "is", "end", "of", "LazyColumn")
        ) { index, string ->
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(2) {
                    Text(
                        "$index th position $string", modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}