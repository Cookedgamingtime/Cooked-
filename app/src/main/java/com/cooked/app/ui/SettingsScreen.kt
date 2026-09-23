package com.cooked.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cooked.app.data.Prefs
import com.cooked.app.logic.UsageStatsHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val ctx = LocalContext.current
    val prefs = remember { Prefs(ctx) }

    var notifOn by remember { mutableStateOf(prefs.notificationsOn) }
    var intensity by remember { mutableStateOf(prefs.intensity) }

    val games = remember { mutableStateListOf<Pair<String, String>>() }
    val selected = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        games.clear()
        games.addAll(UsageStatsHelper.installedGames(ctx))
        selected.clear()
        selected.addAll(prefs.trackedPackages)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF0E0E0E)
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Notifications ──
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Daily roast notification",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            "One notification per day at 9 AM",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    }
                    Switch(
                        checked = notifOn,
                        onCheckedChange = {
                            notifOn = it
                            prefs.notificationsOn = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFFF4D4D),
                            checkedTrackColor = Color(0xFFFF4D4D).copy(alpha = 0.4f)
                        )
                    )
                }
            }

            // ── Intensity ──
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Roast intensity",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IntensityChip("MILD", intensity == 0) {
                            intensity = 0; prefs.intensity = 0
                        }
                        IntensityChip("MEDIUM", intensity == 1) {
                            intensity = 1; prefs.intensity = 1
                        }
                        IntensityChip("UNHINGED", intensity == 2) {
                            intensity = 2; prefs.intensity = 2
                        }
                    }
                }
            }

            // ── Games ──
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Games to track",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        "Tap to select which apps count as games",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(12.dp))

                    if (games.isEmpty()) {
                        Text(
                            "No games detected on this device.",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                    } else {
                        games.forEach { (pkg, label) ->
                            val checked = selected.contains(pkg)
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (checked) selected.remove(pkg)
                                        else selected.add(pkg)
                                        prefs.trackedPackages = selected.toSet()
                                    }
                                    .padding(vertical = 6.dp),
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = {
                                        if (checked) selected.remove(pkg)
                                        else selected.add(pkg)
                                        prefs.trackedPackages = selected.toSet()
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFFFF4D4D),
                                        uncheckedColor = Color.White.copy(alpha = 0.5f),
                                        checkmarkColor = Color.White
                                    )
                                )
                                Spacer(Modifier.padding(4.dp))
                                Text(
                                    label,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun IntensityChip(label: String, active: Boolean, onClick: () -> Unit) {
    val bg = if (active) Color(0xFFFF4D4D) else Color(0xFF2A2A2A)
    val fg = if (active) Color.White else Color.White.copy(alpha = 0.7f)
    Card(
        colors = CardDefaults.cardColors(containerColor = bg),
        shape = RoundedCornerShape(50),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            label,
            color = fg,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}
