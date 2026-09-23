package com.cooked.app.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.cooked.app.data.Prefs
import com.cooked.app.logic.ComplimentGenerator
import com.cooked.app.logic.RoastGenerator
import com.cooked.app.logic.TimeConverter
import com.cooked.app.logic.UsageStatsHelper
import java.util.Calendar
import java.util.concurrent.TimeUnit

class DailyNotifier(appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val prefs = Prefs(applicationContext)
        if (!prefs.notificationsOn) return Result.success()

        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val startOfDay = cal.timeInMillis
        val now = System.currentTimeMillis()

        val minutes = UsageStatsHelper
            .totalGameTime(applicationContext, startOfDay, now, prefs.trackedPackages) / 60000

        val cooked = TimeConverter.format(minutes)
        val real = TimeConverter.formatReal(minutes)
        val roast = RoastGenerator.roast(minutes)
        val comp = ComplimentGenerator.compliment()

        val body = "You played $real today = $cooked in Cooked units. $roast $comp"
        showNotification(applicationContext, body)
        return Result.success()
    }

    private fun showNotification(ctx: Context, body: String) {
        val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val chId = "cooked_daily"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(
                NotificationChannel(chId, "Daily Roast", NotificationManager.IMPORTANCE_DEFAULT)
            )
        }
        val notif = NotificationCompat.Builder(ctx, chId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Cooked daily check-in")
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .build()
        nm.notify(1, notif)
    }

    companion object {
        fun schedule(context: Context) {
            val req = PeriodicWorkRequestBuilder<DailyNotifier>(1, TimeUnit.DAYS)
                .setInitialDelay(computeDelayTo9AM(), TimeUnit.MILLISECONDS)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "cooked_daily",
                ExistingPeriodicWorkPolicy.UPDATE,
                req
            )
        }

        private fun computeDelayTo9AM(): Long {
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
            }
            return target.timeInMillis - now.timeInMillis
        }
    }
    }
