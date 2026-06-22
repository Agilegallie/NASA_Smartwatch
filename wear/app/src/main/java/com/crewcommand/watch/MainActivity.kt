package com.crewcommand.watch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.crewcommand.watch.ui.CrewApp
import com.crewcommand.watch.ui.theme.CrewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrewTheme {
                CrewApp()
            }
        }
    }
}
