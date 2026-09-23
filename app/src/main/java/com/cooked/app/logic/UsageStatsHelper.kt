package com.cooked.app.logic

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager

object UsageStatsHelper {

    fun hasPermission(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    fun totalGameTime(
        context: Context,
        start: Long,
        end: Long,
        packages: Set<String>
    ): Long {
        if (packages.isEmpty()) return 0L
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val events = usm.queryEvents(start, end)
        var lastResume = 0L
        var lastPkg: String? = null
        var total = 0L
        val ev = android.app.usage.UsageEvents.Event()
        while (events.hasNextEvent()) {
            events.getNextEvent(ev)
            when (ev.eventType) {
                android.app.usage.UsageEvents.Event.ACTIVITY_RESUMED -> {
                    if (lastPkg != null && packages.contains(lastPkg)) {
                        total += (ev.timeStamp - lastResume).coerceAtLeast(0)
                    }
                    lastPkg = ev.packageName
                    lastResume = ev.timeStamp
                }
                android.app.usage.UsageEvents.Event.ACTIVITY_PAUSED -> {
                    if (lastPkg != null && packages.contains(lastPkg)
                        && ev.packageName == lastPkg) {
                        total += (ev.timeStamp - lastResume).coerceAtLeast(0)
                        lastPkg = null
                    }
                }
            }
        }
        return total
    }

    fun installedGames(context: Context): List<Pair<String, String>> {
        val pm = context.packageManager
        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.category == android.content.pm.ApplicationInfo.CATEGORY_GAME }
            .map { it.packageName to pm.getApplicationLabel(it).toString() }
    }

    fun labelFor(context: Context, pkg: String): String = try {
        val ai = context.packageManager.getApplicationInfo(pkg, 0)
        context.packageManager.getApplicationLabel(ai).toString()
    } catch (e: Exception) { pkg }
}
