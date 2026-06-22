package com.crewcommand.watch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.crewcommand.watch.ui.components.LabelText
import com.crewcommand.watch.ui.theme.OnSurface
import com.crewcommand.watch.ui.theme.SurfaceHigh
import com.crewcommand.watch.ui.theme.TimelineActive
import com.crewcommand.watch.ui.theme.TimelineExercise
import com.crewcommand.watch.ui.theme.TimelineMeal
import com.crewcommand.watch.ui.theme.TimelinePersonal
import com.crewcommand.watch.ui.theme.TimelineSleep
import com.crewcommand.watch.ui.theme.TimelineWork

@Composable
fun TimelineScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "08/23/2023",
            color = OnSurface,
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("‹", color = OnSurface, fontSize = 16.sp)
            Text(
                text = "Today",
                color = OnSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
            )
            Text("›", color = OnSurface, fontSize = 16.sp)
        }
        LabelText("GMT", modifier = Modifier.padding(start = 18.dp, bottom = 4.dp))

        TimelineItem("06:00", "POSTSLEEP", TimelineSleep)
        TimelineItem("07:30", "Work", TimelineWork)
        TimelineItem("12:00", "Meal", TimelineMeal, active = true, trailing = "🍲")
        TimelineItem("12:30", "Work/Breakfast", TimelineWork, trailing = "⚽")
        TimelineItem("15:30", "Exercise", TimelineExercise, trailing = "🏃")
        TimelineItem("15:30", "Personal", TimelinePersonal, trailing = "🏃")

        LabelText(
            "↑ Personal Time",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            align = TextAlign.Center,
        )
    }
}

@Composable
private fun TimelineItem(
    time: String,
    label: String,
    color: Color,
    active: Boolean = false,
    trailing: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(34.dp)
                .background(SurfaceHigh),
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(color)
                .padding(horizontal = 10.dp, vertical = 6.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$time $label",
                    color = OnSurface,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                if (trailing != null) {
                    Text(trailing, fontSize = 12.sp)
                }
            }
            if (active) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TimelineActive),
                )
            }
        }
    }
}
