package com.example.demoproject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DetailedScreen(navController: NavController, data: String) {
    Column(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxSize()
            .border(5.dp, Color.Blue)
            .padding(20.dp) //From above blue 30dp it will start printing below data.
            .border(2.dp, Color.Red)
            .padding(10.dp) //From above Red border to 10 dp
            .border(1.dp, Color.Magenta)
            .padding(5.dp)
    ) {
        Text("Hey here the data which got from main class & click name")
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            data, modifier = Modifier
                .border(1.dp, Color.White)
                .padding(12.dp)
                .clickable {
                    val dataToSend = MyCustomData("9999", "Preparing compose Navigations")
                    val encodeData = MyCustomDataType.serializeAsValue(dataToSend) //Or using  MyCustomDataType.put() too we can set data.
                    /*
                    * Without encoding if we directly send "dataToSend" class it throws below exception.
                    * java.lang.IllegalArgumentException: Navigation destination that matches route custom_screen/MyCustomData(id=9999, title=Preparing compose Navigations)
                    * cannot be found in the navigation graph ComposeNavGraph(0x0) startDestination={Destination(0x26775c05) route=main_screen}
                    * */
                    navController.navigate(Screen.CustomDataScreen.route + "/${encodeData}")
                }
        )
    }
}