package com.crewcommand.watch.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.crewcommand.watch.ui.components.WatchScaffold
import com.crewcommand.watch.ui.screens.CautionsScreen
import com.crewcommand.watch.ui.screens.CommunicationScreen
import com.crewcommand.watch.ui.screens.HubScreen
import com.crewcommand.watch.ui.screens.SplashScreen
import com.crewcommand.watch.ui.screens.TimelineScreen
import com.crewcommand.watch.ui.screens.TimersScreen

@Composable
fun CrewApp() {
    var screen by rememberSaveable { mutableStateOf(CrewScreen.Splash) }

    if (screen == CrewScreen.Splash) {
        SplashScreen(onEnter = { screen = CrewScreen.Hub })
        return
    }

    WatchScaffold(
        screen = screen,
        onNavigate = { screen = it },
        onSwipe = { forward ->
            if (screen !in CrewScreen.moduleScreens()) return@WatchScaffold
            screen = if (forward) screen.next() else screen.previous()
        },
    ) {
        when (screen) {
            CrewScreen.Splash -> Unit
            CrewScreen.Hub -> HubScreen(onNavigate = { screen = it })
            CrewScreen.Timeline -> TimelineScreen()
            CrewScreen.Cautions -> CautionsScreen()
            CrewScreen.Communication -> CommunicationScreen()
            CrewScreen.Timers -> TimersScreen()
        }
    }
}
