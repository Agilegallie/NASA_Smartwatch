package com.crewcommand.watch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.crewcommand.watch.ui.components.LabelText
import com.crewcommand.watch.ui.theme.CautionText
import com.crewcommand.watch.ui.theme.CautionYellow
import com.crewcommand.watch.ui.theme.ErrorContainer
import com.crewcommand.watch.ui.theme.OnError
import com.crewcommand.watch.ui.theme.OnSurface
import com.crewcommand.watch.ui.theme.OnSurfaceVariant
import com.crewcommand.watch.ui.theme.Secondary
import com.crewcommand.watch.ui.theme.SurfaceHigh

@Composable
fun CautionsScreen() {
    var criticalAcked by remember { mutableStateOf(false) }
    var cautionAcked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LabelText("Filter: All", size = 7)
            LabelText("Priority: All", size = 7, modifier = Modifier.padding(start = 12.dp))
        }

        AppleAlertCard(
            level = "CRITICAL",
            icon = "❗",
            message = "ISS DEORBIT BURN IMMINENT",
            background = ErrorContainer,
            buttonLabel = if (criticalAcked) "ACKNOWLEDGED" else "ACKNOWLEDGE",
            dimmed = criticalAcked,
            onAck = { criticalAcked = true },
        )

        AppleAlertCard(
            level = "CAUTION",
            icon = "⚠",
            message = "SOLAR ARRAY DEPLOYMENT FAILED",
            background = CautionYellow,
            messageColor = CautionText,
            buttonLabel = if (cautionAcked) "ACKNOWLEDGED" else "ACKNOWLEDGE",
            dimmed = cautionAcked,
            onAck = { cautionAcked = true },
        )

        SamsungAlertRow(
            color = ErrorContainer,
            icon = "◎",
            title = "Pressure Low - Airlock",
            time = "12:34",
        )
        SamsungAlertRow(
            color = CautionYellow,
            icon = "⚡",
            title = "Caution Battery Temp High",
            time = "12:35",
            textColor = CautionText,
        )
        SamsungAlertRow(
            color = Secondary,
            icon = "⏻",
            title = "System Restart",
            time = "12:36",
            textColor = Color.Black,
        )
    }
}

@Composable
private fun AppleAlertCard(
    level: String,
    icon: String,
    message: String,
    background: Color,
    buttonLabel: String,
    dimmed: Boolean,
    onAck: () -> Unit,
    messageColor: Color = OnError,
    buttonColor: Color = background.copy(alpha = 0.75f),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        LabelText(level, color = OnSurface, size = 9, modifier = Modifier.padding(start = 4.dp, bottom = 2.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(if (dimmed) background.copy(alpha = 0.55f) else background)
                .padding(10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(icon, fontSize = 14.sp)
                }
                Text(
                    text = message,
                    color = messageColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            if (!dimmed) {
                Text(
                    text = buttonLabel,
                    color = OnSurface,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(buttonColor)
                        .clickable(onClick = onAck)
                        .padding(vertical = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun SamsungAlertRow(
    color: Color,
    icon: String,
    title: String,
    time: String,
    textColor: Color = OnSurface,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(icon, fontSize = 14.sp, modifier = Modifier.padding(end = 6.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = textColor, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
            Text(time, color = textColor.copy(alpha = 0.8f), fontSize = 8.sp)
        }
        Text(
            text = "ACK",
            color = OnSurface,
            fontSize = 8.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceHigh)
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}
