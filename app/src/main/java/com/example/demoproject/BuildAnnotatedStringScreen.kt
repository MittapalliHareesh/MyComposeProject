package com.example.demoproject

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Bullet
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
* Below entire thing is single textView.
*
* Here if you see at very below overall last we are setting style, fontWeight, letterSpacing that is
* applicable entire text.
*
* Here advantage of buildAnnotatedString{ } is overall color set red in style but name alone Hitansh
* comes in with underline, blue background, Italic for this name alone added withStyle() those would be applied.
*
* Similarly for Bullet points use ==> withBulletList{ withBulletListItem(), withBulletListItem() }
*
* and for textView with click just use like below
*   withLink(
                    LinkAnnotation.Url(url = "https://fonts.google.com")
                ) {
                    withStyle(SpanStyle(Color.Blue)) {
                        append("Click on this link to know more about fonts")
                    }
                }

   ==> Last 4th point Used withStyle, withBulletList and withLink in one shot.
* */
@Composable
fun BuildAnnotatedStringScreen() {
    val fontFamily = FontFamily(
        Font(R.font.googlesans_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.googlesans_italic, FontWeight.Black, FontStyle.Italic),
        Font(R.font.googlesans_medium, FontWeight.Thin, FontStyle.Italic),
        Font(R.font.googlesans_semibold, FontWeight.SemiBold, FontStyle.Italic),
        Font(R.font.googlesans_mediumitalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(R.font.googlesans_semiboldltalic, FontWeight.Black, FontStyle.Italic)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .border(border = BorderStroke(2.dp, Color.Green))
            .padding(30.dp)
    )
    {
        /*Text(
            text = "Here we are building annotated string below.",
            fontSize = 20.sp,
            style = TextStyle(Color.Magenta, fontStyle = FontStyle.Italic)
        )*/

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = buildAnnotatedString {
                append("\"My name is ")

                //1. Using withStyle highlighting name.
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 35.sp,
                        letterSpacing = 2.sp,
                        background = Color.Blue,
                        color = Color.Cyan,
                        textDecoration = TextDecoration.Underline // can do like TextDecoration.Underline + TextDecoration.LineThrough
                    )
                ) {
                    append(" Hitansh ")
                }
                append(" i am studying KG1 now.\"")

                //2. Wants to print data in bullets format use withBullet and inside that use withBulletItems
                withBulletList(bullet = Bullet.Default) {
                    withStyle(SpanStyle(Color.Black)) {
                        append("\nHere is small intro about my family\n")
                    }

                    withBulletListItem {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = fontFamily,
                                color = Color.Green,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("My GrandFather name is M RangaRao")
                        }
                    }

                    withBulletListItem {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = fontFamily,
                                color = Color.Green,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("My GrandMother name is M Lakshmi")
                        }
                    }
                }

                //3. Using withLink like click on link navigate to other page.
                withLink(
                    LinkAnnotation.Url(url = "https://fonts.google.com")
                ) {
                    withStyle(SpanStyle(Color.Blue)) {
                        append("\nClick on this link to know more about fonts")
                    }
                }

                append("\n")

                //4. We can combine withStyle , withLink and withBulletItems
                withBulletList {
                    withStyle(style = SpanStyle(fontFamily = FontFamily.Cursive)) {
                        withBulletListItem {
                            append("Here we are combining withStyle, withBulletList(withBulletListItem) and withLink")
                            withLink(
                                LinkAnnotation.Url(url = "https://developer.android.com/develop/ui/compose/text")
                            ) {
                                withStyle(SpanStyle(fontFamily = fontFamily)) {
                                    append("\n\nMore about compose TextView click me")
                                }
                            }
                        }
                    }
                }
            },
            style = TextStyle(Color.Red, fontSize = 25.sp, fontStyle = FontStyle.Normal),
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp //Between letter 1sp space.
        )
    }
}