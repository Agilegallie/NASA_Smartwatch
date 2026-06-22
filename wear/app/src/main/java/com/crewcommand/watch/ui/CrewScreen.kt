package com.crewcommand.watch.ui

enum class CrewScreen(val title: String, val navLabel: String) {
    Splash("Splash", ""),
    Hub("Menu", "Home"),
    Timeline("Timeline", "Timeline"),
    Cautions("Alerts", "Alerts"),
    Communication("Comm", "Comm"),
    Timers("Timers", "Timers");

    fun next(): CrewScreen {
        val modules = moduleScreens()
        val index = modules.indexOf(this).coerceAtLeast(0)
        return modules[(index + 1) % modules.size]
    }

    fun previous(): CrewScreen {
        val modules = moduleScreens()
        val index = modules.indexOf(this).coerceAtLeast(0)
        return modules[(index - 1 + modules.size) % modules.size]
    }

    companion object {
        fun moduleScreens(): List<CrewScreen> = listOf(Timeline, Cautions, Communication, Timers)
    }
}
