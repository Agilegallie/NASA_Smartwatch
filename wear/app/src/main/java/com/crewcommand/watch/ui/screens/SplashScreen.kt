package com.crewcommand.watch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.crewcommand.watch.R
import com.crewcommand.watch.ui.theme.Primary
import com.crewcommand.watch.ui.theme.Surface
import com.crewcommand.watch.ui.theme.Tertiary

@Composable
fun SplashScreen(onEnter: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "CREW COMMAND",
            color = Primary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
        )
        Text(
            text = "ISS OPERATIONS",
            color = Tertiary,
            fontSize = 7.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 2.dp, bottom = 8.dp),
        )

        Image(
            painter = painterResource(R.drawable.ic_splash_space),
            contentDescription = "International Space Station above Earth",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(108.dp)
                .clip(RoundedCornerShape(54.dp)),
        )

        Text(
            text = "ENTER",
            color = Surface,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Tertiary)
                .clickable(onClick = onEnter)
                .padding(horizontal = 28.dp, vertical = 10.dp),
        )
    }
}
