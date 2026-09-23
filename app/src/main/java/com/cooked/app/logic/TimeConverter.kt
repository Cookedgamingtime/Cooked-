package com.cooked.app.logic

object TimeConverter {

    fun toCookedHours(realMinutes: Long): Long = realMinutes

    fun format(realMinutes: Long): String = "${realMinutes} hrs"

    fun formatReal(realMinutes: Long): String {
        val h = realMinutes / 60
        val m = realMinutes % 60
        return if (h > 0) "${h}h ${m}m" else "${m}m"
    }

    fun tier(realMinutes: Long): Int = when {
        realMinutes < 30 -> 0
        realMinutes < 60 -> 1
        realMinutes < 180 -> 2
        realMinutes < 360 -> 3
        realMinutes < 600 -> 4
        else -> 5
    }
}
