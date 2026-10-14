package com.example.demoproject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column {
        Row {
            Row(
                Modifier
                    .padding()
                    .border(5.dp, Color.Magenta)
                    .fillMaxSize(0.5F)
                    //.requiredWidth(600.dp) -->If we add this one what will happen is force fully it takes 600 dp even if screen size is 400dp.
                    //If we add normal width with 600.dp if our screen doesn't 600 then max width it will assign. But requiredWidth is forcefully applies mentioned width.
                    //.requireWidthIn() is also there, it needs min and max values. But it will never be smaller than min dp and never wider than max dp.
                    .fillMaxHeight(0.5f)
                    .background(Color.Cyan),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    //Apart from above modifiers, this offset applies to only this textView.
                    //move starting place from current position 10 dp right side & top 3dp down.
                    Text("Hello", modifier = Modifier.offset(10.dp, 3.dp))
                    Spacer(modifier = Modifier.height(100.dp)) //This just create a space b/w both textViews
                    Text(
                        "Constraint Layout Ex",
                        modifier = Modifier
                            .border(2.dp, Color.Magenta)
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(Screen.ConstraintLayoutScreen.route)
                            }
                    )
                }
            }

            Row(
                Modifier
                    .padding() //Here vertical = 40.dp, horizontal = 20.dp  gives left and right side padding. Left only gives left side padding
                    //Interesting one, what happens if we declare .fillMaxSize(0.5F) here is , Already above row occupied 50% of width.
                    //Rest of the (50%) space it occupies 50% i.e it won't come rest half of screen.
                    //so either declare like just .fillMaxSize() then it would rest half part + to control height fillMaxHeight(0.5f) else below height approach.
                    .weight(1F)
                    .fillMaxHeight(0.5f)
                    .background(Color.Red)
                    .border(5.dp, Color.Blue),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        "Hello, \n\nClick here to build annotated String",
                        Modifier
                            .padding(10.dp)
                            //.clickable{} //Here clickable applies to entire text (Means border+text).
                            .border(2.dp, Color.White)
                            .padding(10.dp) // Here order matters,if we add padding before border its like normal external padding.
                            //if we add like this post border step then Draws the border on the outer edge, then pushes the text content inward. This creates internal padding
                            .clickable {  //Here clickable applies to just text
                                navController.navigate(Screen.AnnotatedStringScreen.route)
                            }
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        "Click to render ListViews",
                        modifier = Modifier
                            .padding(12.dp)
                            .border(2.dp, Color.White)
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(Screen.ListsScreen.route)
                            }
                    )
                }
            }
        }

        Row {
            Row(
                Modifier
                    .padding()
                    .weight(1F)
                    .fillMaxHeight() //If we declare .fillMaxHeight(0.5f) like above here it will take rest 50% rest of place.
                    .background(Color.Green)
                    .border(5.dp, Color.Red),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val count = remember { mutableIntStateOf(0) }

                Column {
                    Text(
                        "Click me ${count.intValue}",
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(15.dp)
                            .border(2.dp, Color.White)
                            .padding(10.dp)
                            .clickable {
                                //Since it's a mutable state, get a value out of it.
                                count.intValue++
                                navController.navigate(
                                    Screen.DetailedScreen.route + "/Hitansh" + "?optionalData=${count.intValue}"
                                )
                            }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Click above to play around with boarders and paddings.In a new screen.",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Row(
                Modifier
                    .padding()
                    .weight(1F)
                    .fillMaxHeight()
                    .border(5.dp, Color.Blue)
                    .background(Color.Yellow),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        "Click me to load local ImageView", modifier = Modifier
                            .padding(15.dp)
                            .border(
                                4.dp, Color.Magenta
                            )
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(Screen.ImageViewScreen.route)
                            }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Click me to browse image and load image.",
                        modifier = Modifier
                            .padding(15.dp)
                            .border(3.dp, Color.Magenta)
                            .padding(5.dp)
                            .clickable {
                                navController.navigate(Screen.BrowseImageViewScreen.route)
                            }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Via URL download Image and load it.",
                        modifier = Modifier
                            .padding(15.dp)
                            .border(3.dp, Color.Magenta)
                            .padding(5.dp)
                            .clickable {
                                navController.navigate(Screen.DownloadImageViewScreen.route)
                            }
                    )
                }
            }
        }
    }
}