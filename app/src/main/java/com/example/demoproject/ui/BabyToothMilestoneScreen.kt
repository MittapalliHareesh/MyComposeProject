package com.example.demoproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoproject.R
import com.example.demoproject.ui.theme.DemoProjectTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BabyToothMilestoneScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        // Baby Image Placeholder
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.baby_content_description),
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.Center)
                .offset(x = 20.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Red)
        )

        // Circular Teeth on the left
        ToothCircle(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 120.dp)
        )

        // Banner at top right
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BannerText(text = stringResource(id = R.string.banner_i_got))
            BannerText(text = stringResource(id = R.string.banner_my))
            BannerText(text = stringResource(id = R.string.banner_tooth))
        }

        // Scattered items on the right
        ToothbrushAndTeeth(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 80.dp)
        )

        // Some extra teeth
        Image(
            painter = painterResource(id = R.drawable.ic_tooth),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
                .offset(x = (-40).dp)
                .size(36.dp)
                .rotate(15f)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_tooth),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
                .offset(x = (-60).dp)
                .size(32.dp)
                .rotate(-10f)
        )
    }
}

@Composable
fun ToothCircle(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val toothCount = 20
        val radius = 140.dp
        for (i in 0 until toothCount) {
            val angle = (i * 260 / (toothCount - 1)).toDouble() - 130 // Circular arc
            val x = radius.value * cos(Math.toRadians(angle)).toFloat()
            val y = radius.value * sin(Math.toRadians(angle)).toFloat()

            Image(
                painter = painterResource(id = R.drawable.ic_tooth),
                contentDescription = stringResource(id = R.string.tooth_content_description),
                modifier = Modifier
                    .offset(x = x.dp, y = y.dp)
                    .size(36.dp)
                    .rotate(angle.toFloat() + 90f)
            )
        }
    }
}

@Composable
fun BannerText(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 4.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun ToothbrushAndTeeth(modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(200.dp)) {
        // Toothbrush Placeholder
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(width = 12.dp, height = 120.dp)
                .background(Color(0xFFA5D6A7)) // Mint green handle
                .rotate(15f)
        ) {
            // Bristles
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(width = 20.dp, height = 30.dp)
                    .background(Color.White)
                    .offset(y = (-5).dp)
            )
        }

        // A few teeth around it
        Image(
            painter = painterResource(id = R.drawable.ic_tooth),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(32.dp)
                .rotate(45f)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_tooth),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(32.dp)
                .rotate(-30f)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_tooth),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-30).dp, y = 40.dp)
                .size(28.dp)
                .rotate(10f)
        )
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 480)
@Composable
fun BabyToothMilestoneScreenPreview() {
    DemoProjectTheme {
        BabyToothMilestoneScreen()
    }
}
