package com.crewcommand.watch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.crewcommand.watch.ui.components.LabelText
import com.crewcommand.watch.ui.components.ValueText
import com.crewcommand.watch.ui.theme.CommActive
import com.crewcommand.watch.ui.theme.CommLost
import com.crewcommand.watch.ui.theme.OnSurface
import com.crewcommand.watch.ui.theme.OnSurfaceVariant
import com.crewcommand.watch.ui.theme.SurfaceHigh

@Composable
fun CommunicationScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(SurfaceHigh.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("○", color = OnSurfaceVariant, fontSize = 10.sp)
                Text(
                    text = "COMM LINK ACTIVE",
                    color = CommActive,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
                Text("▮▮▯", color = CommActive, fontSize = 14.sp, letterSpacing = 2.sp)
                Text(
                    text = "Voice/Video OK to Houston",
                    color = OnSurface,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp),
                )
                Text(
                    text = "NO COMM - LOS",
                    color = CommLost,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 6.dp),
                )
                Text(
                    text = "Next AOS in 00:14:32",
                    color = OnSurface,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            CommShortcut("AOS", "〰")
            CommShortcut("Voice", "🔊")
            CommShortcut("Text", "💬")
            CommShortcut("Video", "📹")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ValueText(
                "Next Window",
                size = 8,
                bold = false,
                color = OnSurfaceVariant,
                modifier = Modifier.weight(1f),
            )
            ValueText("02:45:12", size = 10, modifier = Modifier)
        }
    }
}

@Composable
private fun CommShortcut(label: String, icon: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 14.sp)
        LabelText(label, size = 7, align = TextAlign.Center)
    }
}
