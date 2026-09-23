package com.cooked.app.data

import android.content.Context
import androidx.core.content.edit

class Prefs(context: Context) {
    private val sp = context.getSharedPreferences("cooked_prefs", Context.MODE_PRIVATE)

    var trackedPackages: Set<String>
        get() = sp.getStringSet("tracked", emptySet()) ?: emptySet()
        set(v) = sp.edit { putStringSet("tracked", v) }

    var notificationsOn: Boolean
        get() = sp.getBoolean("notif", true)
        set(v) = sp.edit { putBoolean("notif", v) }

    var intensity: Int
        get() = sp.getInt("intensity", 2)
        set(v) = sp.edit { putInt("intensity", v) }
}
