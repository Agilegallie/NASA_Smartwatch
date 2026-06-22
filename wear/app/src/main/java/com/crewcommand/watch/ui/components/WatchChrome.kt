package com.crewcommand.watch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.crewcommand.watch.ui.CrewScreen
import com.crewcommand.watch.ui.theme.OnSurface
import com.crewcommand.watch.ui.theme.OnSurfaceVariant
import com.crewcommand.watch.ui.theme.Surface
import com.crewcommand.watch.ui.theme.SurfaceHigh
import com.crewcommand.watch.ui.theme.Tertiary

@Composable
fun WatchScaffold(
    screen: CrewScreen,
    onNavigate: (CrewScreen) -> Unit,
    onSwipe: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val isHub = screen == CrewScreen.Hub
    val isModule = screen in CrewScreen.moduleScreens()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .pointerInput(screen) {
                if (!isModule) return@pointerInput
                var totalDrag = 0f
                detectHorizontalDragGestures(
                    onDragStart = { totalDrag = 0f },
                    onHorizontalDrag = { _, dragAmount -> totalDrag += dragAmount },
                    onDragEnd = {
                        when {
                            totalDrag < -50f -> onSwipe(true)
                            totalDrag > 50f -> onSwipe(false)
                        }
                    },
                )
            },
    ) {
        if (isModule) {
            ModuleHeader(
                title = screen.title,
                onMenu = { onNavigate(CrewScreen.Hub) },
                onNext = { onSwipe(true) },
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .then(if (isModule) Modifier.verticalScroll(rememberScrollState()) else Modifier),
        ) {
            content()
        }

        if (isHub) {
            HubPaginationDots()
        } else if (isModule) {
            ModuleBottomNav(screen = screen, onNavigate = onNavigate)
        }
    }
}

@Composable
fun ModuleHeader(
    title: String,
    onMenu: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "⌂",
            color = Tertiary,
            fontSize = 16.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onMenu)
                .padding(horizontal = 6.dp, vertical = 2.dp),
        )
        Text(
            text = title.uppercase(),
            color = OnSurface,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "›",
            color = OnSurfaceVariant,
            fontSize = 16.sp,
            modifier = Modifier
                .clickable(onClick = onNext)
                .padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}

@Composable
private fun HubPaginationDots() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(5) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(if (index == 2) 7.dp else 5.dp)
                    .clip(CircleShape)
                    .background(
                        if (index in 1..3) OnSurface else OnSurfaceVariant.copy(alpha = 0.35f),
                    ),
            )
        }
    }
}

@Composable
private fun ModuleBottomNav(
    screen: CrewScreen,
    onNavigate: (CrewScreen) -> Unit,
) {
    // Four modules only — Home is the ⌂ control in the header (5 icons clip on round displays).
    val tabs = listOf(
        CrewScreen.Timeline to "📅",
        CrewScreen.Cautions to "⚠",
        CrewScreen.Communication to "📡",
        CrewScreen.Timers to "⏱",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, end = 26.dp, bottom = 6.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
            .background(SurfaceHigh.copy(alpha = 0.92f))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEach { (route, icon) ->
            val active = route == screen
            Text(
                text = icon,
                fontSize = if (active) 13.sp else 11.sp,
                color = if (active) Tertiary else OnSurfaceVariant,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onNavigate(route) }
                    .padding(horizontal = 6.dp, vertical = 4.dp),
            )
        }
    }
}

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = OnSurfaceVariant,
    align: TextAlign = TextAlign.Start,
    size: Int = 8,
) {
    Text(
        text = text,
        color = color,
        fontSize = size.sp,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 0.5.sp,
        textAlign = align,
        modifier = modifier,
    )
}

@Composable
fun ValueText(
    text: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = OnSurface,
    size: Int = 12,
    bold: Boolean = true,
) {
    Text(
        text = text,
        color = color,
        fontSize = size.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = if (bold) FontWeight.SemiBold else FontWeight.Normal,
        modifier = modifier,
    )
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 3.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceHigh)
            .padding(8.dp),
    ) {
        content()
    }
}
