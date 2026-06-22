package com.crewcommand.watch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.crewcommand.watch.ui.CrewScreen
import com.crewcommand.watch.ui.theme.OnSurface
import com.crewcommand.watch.ui.theme.SurfaceHigh
import com.crewcommand.watch.ui.theme.TimerAccent

@Composable
fun HubScreen(onNavigate: (CrewScreen) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "🛰", fontSize = 22.sp)
        Text(
            text = "GMT 12:00",
            color = OnSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 6.dp, bottom = 14.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            MenuTile(
                modifier = Modifier.weight(1f),
                icon = "📅",
                label = "Timeline",
                labelColor = OnSurface,
                onClick = { onNavigate(CrewScreen.Timeline) },
            )
            MenuTile(
                modifier = Modifier.weight(1f),
                icon = "⚠",
                label = "Cautions",
                labelColor = OnSurface,
                onClick = { onNavigate(CrewScreen.Cautions) },
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            MenuTile(
                modifier = Modifier.weight(1f),
                icon = "📊",
                label = "Comm Status",
                labelColor = OnSurface,
                onClick = { onNavigate(CrewScreen.Communication) },
            )
            MenuTile(
                modifier = Modifier.weight(1f),
                icon = "⏱",
                label = "Timers",
                labelColor = TimerAccent,
                onClick = { onNavigate(CrewScreen.Timers) },
            )
        }
    }
}

@Composable
private fun MenuTile(
    icon: String,
    label: String,
    labelColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceHigh)
            .clickable(onClick = onClick)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = icon, fontSize = 26.sp)
        Text(
            text = label,
            color = labelColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
