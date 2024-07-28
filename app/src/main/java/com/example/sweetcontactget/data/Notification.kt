package com.example.sweetcontactget.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.sweetcontactget.DetailActivity
import com.example.sweetcontactget.MainActivity
import com.example.sweetcontactget.R
import com.example.sweetcontactget.StartActivity

class MyNotification(private val context: Context) {

    private var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        checkNotificationPermission(context)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    enableVibration(true)
                    description = "description"
                }

        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun checkNotificationPermission(context: Context): Boolean {
        val callPermission =
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
        return callPermission == PackageManager.PERMISSION_GRANTED
    }
    fun deliverNotification(title: String, content: String, sweetieId: Int) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("sweetieId", sweetieId)
        }

        val stackBuilder = TaskStackBuilder.create(context).apply {
            addNextIntentWithParentStack(Intent(context, StartActivity::class.java))
            addNextIntentWithParentStack(Intent(context, MainActivity::class.java))
            addNextIntent(intent)
        }

        val pendingIntent = stackBuilder.getPendingIntent(
            NOTIFICATION_ID,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val sweetieInfo = DataObject.getSweetieInfo(sweetieId)
        val bitmap = drawableToBitmap(sweetieInfo.imgSrc!!)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.heart_full)
            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null as Bitmap?))

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
        const val NOTIFICATION_ID = 0
    }
}