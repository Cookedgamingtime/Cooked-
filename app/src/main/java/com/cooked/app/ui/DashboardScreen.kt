package com.cooked.app.ui

import android.content.Intent
import android.provider.Settings
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
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
import com.cooked.app.logic.ComplimentGenerator
import com.cooked.app.logic.RoastGenerator
import com.cooked.app.logic.TimeConverter
import com.cooked.app.logic.UsageStatsHelper
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onSettings: () -> Unit) {
    val ctx = LocalContext.current
    val prefs = remember { Prefs(ctx) }

    var hasPerm by remember { mutableStateOf(UsageStatsHelper.hasPermission(ctx)) }
    var todayMin by remember { mutableLongStateOf(0L) }
    var weekMin by remember { mutableLongStateOf(0L) }
    var roast by remember { mutableStateOf("") }
    var comp by remember { mutableStateOf("") }

    LaunchedEffect(hasPerm, prefs.trackedPackages) {
        if (hasPerm) {
            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
            val startOfDay = cal.timeInMillis
            val week = startOfDay - 6L * 24 * 60 * 60 * 1000
            val now = System.currentTimeMillis()
            todayMin = UsageStatsHelper
                .totalGameTime(ctx, startOfDay, now, prefs.trackedPackages) / 60000
            weekMin = UsageStatsHelper
                .totalGameTime(ctx, week, now, prefs.trackedPackages) / 60000
            roast = RoastGenerator.roast(todayMin)
            comp = ComplimentGenerator.compliment()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("COOKED", fontWeight = FontWeight.Black, fontSize = 24.sp)
                },
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
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
            if (!hasPerm) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD93D)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Permission needed",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Cooked needs Usage Access to read your game time.",
                            color = Color.Black
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                ctx.startActivity(
                                    Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Grant permission")
                        }
                    }
                }
            }

            if (prefs.trackedPackages.isEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF9B5DE5)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "No games selected yet",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Tap the gear icon and pick which games count.",
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            BigStat("TODAY", TimeConverter.formatReal(todayMin), "real time")
            BigStat("COOKED UNITS", TimeConverter.format(todayMin), "the meme number")
            BigStat("THIS WEEK", TimeConverter.formatReal(weekMin), "7-day total")

            if (roast.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF4D4D)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            "THE ROAST",
                            color = Color.White.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            roast,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            "ok but",
                            color = Color(0xFFFFD93D),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            comp,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BigStat(label: String, value: String, sub: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                label,
                color = Color(0xFFFF4D4D),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                value,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 36.sp
            )
            Spacer(Modifier.height(2.dp))
            Text(
                sub,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
    }
}
