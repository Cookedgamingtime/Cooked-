package com.cooked.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cooked.app.ui.DashboardScreen
import com.cooked.app.ui.SettingsScreen
import com.cooked.app.ui.theme.CookedTheme
import com.cooked.app.work.DailyNotifier

class MainActivity : ComponentActivity() {

    private val notifPerm = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notifPerm.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        DailyNotifier.schedule(this)
        setContent {
            CookedTheme {
                val nav = rememberNavController()
                NavHost(nav, startDestination = "dash") {
                    composable("dash") {
                        DashboardScreen(onSettings = { nav.navigate("settings") })
                    }
                    composable("settings") {
                        SettingsScreen(onBack = { nav.popBackStack() })
                    }
                }
            }
        }
    }
}
