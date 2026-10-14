package com.example.demoproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade

//Here we download the image via url and load it. Will download using Coil library and render using AsyncImage.
//Previously we saw if image is in local we use painterResource

//1. We need to add the coil dependency.    //implementation("io.coil-kt.coil3:coil-compose:3.0.4")
//2. We need to add the interent permission. // <uses-permission android:name="android.permission.INTERNET" />
//3. Load the image Using AsyncImage.

@Composable
fun DownloadImageViewScreen() {
    //To capture a url from editText.
    /* rememberSaveable { mutableStateOf("") } ==> handles screen orientation and saves data in bundle where as
    *  remember { mutableStateOf("") } ==> just stores temporarily and resets to empty when screen changes. */
    var inputUrl by rememberSaveable { mutableStateOf("") }

    var imageUrlToLoad by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), //Enable scrolling if image is large
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //1) EditText impl using OutlinedTextField
        OutlinedTextField(
            value = inputUrl,
            onValueChange = { inputUrl = it },
            label = {
                Text(
                    text = "Enter the Image Url",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 15.sp
                )
            },
            placeholder = { Text("https://www.shutterstock.com/image-photo/little-baby-krishna-eating-makhan-600w-2491547851.jpg") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painterResource(android.R.drawable.ic_menu_edit),
                    contentDescription = "Edit icon"
                )
            },
        )

        Spacer(modifier = Modifier.height(15.dp))

        //2) Download text button
        Button(
            onClick = { imageUrlToLoad = inputUrl }
        ) {
            Text(
                text = "Download And Load Image",
                color = Color.Magenta,
                style = TextStyle(fontSize = 20.sp, fontStyle = FontStyle.Italic)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        //3) Load image
        /*
        * Here we simply write  if(imageUrlToLoad.isEmpty()) AysncImage{} else Text()
        *
        * But Using AnimatedVisibility is an excellent approach to keep the UI smooth and prevent sudden structural layout shifts.
        * */

        AnimatedVisibility(imageUrlToLoad.isEmpty()) {
            Text("\"Enter a valid link and click the button to preview.\"", fontSize = 17.sp)
        }

        /*
        * Here a couple of things to remember.
        *   1) There AsyncImage and SubcomposeAsyncImage between those AsyncImage is faster. But in AsyncImage error accepts only painterResource
        *      but SubcomposeAsyncImage in error we can give text too.
        *
        *   2) Important is,
        *      Generally below     model = imageUrlToLoad  --This should work.
        *      But it depends on URL what your giving, Example if we give above placeHolder mentioned URL it won't download because
        *      "Some Websites security blocks third-party image libraries like Coil from scraping or downloading images from standard webpage wrappers"
        *
        *      To fix this create ImageRequest builder object and add httpHeaders (NetworkHeaders from coil) with some random userAgent.
        *
        *      Shutterstock and similar sites have "bot protection." If they see a request with no User-Agent (or one that says "Coil/3.0"),
        *      they assume it's a script trying to steal images and block it.
        * */
        AnimatedVisibility(imageUrlToLoad.isNotEmpty()) {
            AsyncImage( //SubcomposeAsyncImage()
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrlToLoad)
                    .httpHeaders(
                        NetworkHeaders.Builder()
                            .set(
                                "User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
                            )
                            .build()
                    )
                    .crossfade(true) // Enables the smooth fade-in transition
                    .build(),
                contentDescription = "Download image from url",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = Color.LightGray),
                contentScale = ContentScale.Fit,
                error = painterResource(android.R.drawable.ic_input_delete),
                placeholder = painterResource(android.R.drawable.ic_menu_gallery)
            )
        }
    }
}