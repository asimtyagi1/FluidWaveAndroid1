package com.example.fluidwave

import android.app.Notification
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager

class WaveNotificationListener : NotificationListenerService() {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var windowManager: WindowManager? = null
    private var waveView: FluidWaveView? = null
    private var removeTask: Runnable? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null || sbn.packageName == packageName) return
        if (!Settings.canDrawOverlays(this)) return

        // Notification content is intentionally not read or displayed.
        mainHandler.post { showWave() }
    }

    private fun showWave() {
        val wm = windowManager ?: return
        try {
            if (waveView == null) {
                waveView = FluidWaveView(this)
            } else {
                (waveView?.parent as? android.view.ViewGroup)?.removeView(waveView)
            }

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.TOP or Gravity.START
                windowAnimations = 0
            }

            wm.addView(waveView, params)
            waveView?.startAnimation()

            removeTask?.let { mainHandler.removeCallbacks(it) }
            removeTask = Runnable { hideWave() }
            mainHandler.postDelayed(removeTask!!, 2600L)
        } catch (_: Exception) {
            // Avoid crashing the notification listener if Android rejects an overlay.
        }
    }

    private fun hideWave() {
        try {
            waveView?.let { windowManager?.removeView(it) }
        } catch (_: Exception) {
        }
        waveView = null
    }

    override fun onDestroy() {
        removeTask?.let { mainHandler.removeCallbacks(it) }
        mainHandler.post { hideWave() }
        super.onDestroy()
    }
}
