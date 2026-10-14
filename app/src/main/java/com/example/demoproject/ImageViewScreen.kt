package com.example.demoproject

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*Here will use Box concept which is different from Column and Row.
* Box would be used whenever we want to overlay views.Like wants render imageView on top of text in that cases we use this.
*
* To load local images we need {painterResource} here from server images we use { Coil library AsyncImage } concept.
* */
@Composable
fun ImageViewScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        //Here we want to load image and then on top of text so lets take one card. And then pass parameters.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 10.dp)
        )
        {
            ImageCard(
                painter = painterResource(R.drawable.img), //As mention to load localImages we use painterResource in compose.
                contentDescription = stringResource(R.string.overlay_string),
                title = stringResource(R.string.overlay_string),
            )
        }

        Text(
            text = "=Here below browse an image & load it.=",
            Modifier.padding(start = 30.dp),
            fontSize = 20.sp,
            style = TextStyle(Color.Red)
        )
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String, //For blind people voice.
    title: String, //Which will display on top of image.
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Magenta)
    ) {
        Box {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
            )
            //Here before adding Text we create another box which would be on top of Image and where background would be transparent.
        /*    Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Red
                            ), startY = 250f
                        )
                    )
            ) {
                //Text(text = title, style = TextStyle(Color.White, fontSize = 16.sp))
            }*/
            Text(
                text = title,
                style = TextStyle(Color.Yellow, fontSize = 25.sp),
                modifier = Modifier.padding(start = 20.dp, top = 320.dp)
            )
        }
    }
}