package com.example.demoproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.widget.ConstraintLayout

@Composable
fun ConstraintLayoutScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.LightGray)
    ) {
        //1. Create a references
        val (productImage, titleTxt, subTitleTxt, priceBadge, btnApply, btnPay) = createRef()

        //2. Guideline (Vertical Anchor at 35% width of the screen)
        val imageEndGuideLine = createGuidelineFromStart(0.35f)

        //Product Image constrained to the left side up to the guideline
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .constrainAs(productImage) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(imageEndGuideLine)
                    width = Dimension.fillToConstraints //Its like 0dp ( Fill space up to guideline)
                    height = Dimension.fillToConstraints
                }
        )
        //Product Title
        Text(
            text = "Wireless Headphones max pro",
            modifier = Modifier.constrainAs(titleTxt) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(imageEndGuideLine, margin = 16.dp)
                
            }
        )

        //3.
    }
}