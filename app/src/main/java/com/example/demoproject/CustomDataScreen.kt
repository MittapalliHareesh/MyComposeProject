package com.example.demoproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomDataScreen(customData: MyCustomData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    )
    {
        Text(customData.title)
        Spacer(modifier = Modifier.height(20.dp))
        Text(customData.id)
    }
}