package com.crewcommand.watch.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

val Surface = Color(0xFF000000)
val SurfaceHigh = Color(0xFF1C1C1E)
val SurfaceLow = Color(0xFF111111)
val OnSurface = Color(0xFFFFFFFF)
val OnSurfaceVariant = Color(0xFFAEAEB2)
val Primary = Color(0xFFFFB59A)
val PrimaryContainer = Color(0xFFFF5C00)
val Secondary = Color(0xFF13FF43)
val Tertiary = Color(0xFF4CD6FF)
val TimerAccent = Color(0xFF5AC8FA)
val ErrorContainer = Color(0xFFFF3B30)
val OnError = Color(0xFFFFFFFF)
val CautionYellow = Color(0xFFFFCC00)
val CautionText = Color(0xFF1C1C1E)
val Outline = Color(0xFF3A3A3C)

val TimelineSleep = Color(0xFF34C759)
val TimelineWork = Color(0xFF007AFF)
val TimelineMeal = Color(0xFF48484A)
val TimelineExercise = Color(0xFFAC8E68)
val TimelinePersonal = Color(0xFFAF52DE)
val TimelineActive = Color(0xFFFF9500)

val CommActive = Color(0xFF34C759)
val CommLost = Color(0xFFFF3B30)
val SamsungBlue = Color(0xFF0A84FF)

private val crewColors = Colors(
    primary = Tertiary,
    primaryVariant = SamsungBlue,
    secondary = Secondary,
    secondaryVariant = TimerAccent,
    background = Surface,
    surface = SurfaceHigh,
    error = ErrorContainer,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = OnSurface,
    onSurface = OnSurface,
    onError = OnError,
)

@Composable
fun CrewTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = crewColors, content = content)
}
