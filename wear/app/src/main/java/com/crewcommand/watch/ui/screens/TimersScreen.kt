package com.crewcommand.watch.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.crewcommand.watch.ui.components.LabelText
import com.crewcommand.watch.ui.theme.OnSurface
import com.crewcommand.watch.ui.theme.SamsungBlue
import com.crewcommand.watch.ui.theme.Surface
import com.crewcommand.watch.ui.theme.SurfaceHigh

@Composable
fun TimersScreen() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Timers",
            color = OnSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TimerRing(progress = 0.55f, label = "45min\nremaining")
            TimerRing(progress = 0.72f, label = "45min\nremaining")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            QuickTimerChip("Exercise 2hr", modifier = Modifier.weight(1f))
            QuickTimerChip("Meal 30min", modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            QuickTimerChip("Next Activity", wide = true)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceHigh)
                .padding(horizontal = 10.dp, vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LabelText("Add New Timer", size = 9, color = OnSurface)
                Text("›", color = OnSurface, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            PresetChip("5min", selected = false)
            PresetChip("10min", selected = true)
            PresetChip("15min", selected = false)
            PresetChip("30min", selected = false)
            PresetChip("1hr", selected = false)
        }
    }
}

@Composable
private fun TimerRing(progress: Float, label: String) {
    Box(
        modifier = Modifier.size(72.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(72.dp)) {
            val stroke = 5.dp.toPx()
            val diameter = size.minDimension - stroke
            val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
            val arcSize = Size(diameter, diameter)
            drawArc(
                color = SurfaceHigh,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke),
            )
            drawArc(
                color = SamsungBlue,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )
        }
        Text(
            text = label,
            color = OnSurface,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            lineHeight = 10.sp,
        )
    }
}

@Composable
private fun QuickTimerChip(text: String, modifier: Modifier = Modifier, wide: Boolean = false) {
    Text(
        text = text,
        color = OnSurface,
        fontSize = 8.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .then(if (wide) Modifier.fillMaxWidth(0.7f) else Modifier)
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceHigh)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    )
}

@Composable
private fun PresetChip(text: String, selected: Boolean) {
    Text(
        text = text,
        color = if (selected) Surface else OnSurface,
        fontSize = 8.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) OnSurface else SurfaceHigh)
            .padding(horizontal = 10.dp, vertical = 6.dp),
    )
}
