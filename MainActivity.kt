package com.example.fluidwave

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 32, 28, 32)
            setBackgroundColor(Color.rgb(12, 17, 29))
        }

        val title = TextView(this).apply {
            text = "Fluid Wave"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        val subtitle = TextView(this).apply {
            text = "Notification par full-screen fluid animation"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 28)
        }
        status = TextView(this).apply {
            textSize = 14f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 22)
        }

        val overlayButton = makeButton("1. Allow display over other apps") {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
        val notificationButton = makeButton("2. Enable notification access") {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        layout.addView(title, matchWrap())
        layout.addView(subtitle, matchWrap())
        layout.addView(overlayButton, matchWrap())
        layout.addView(notificationButton, matchWrap())
        layout.addView(status, matchWrap())

        val note = TextView(this).apply {
            text = "Dono permissions dene ke baad app ko background mein rehne dein. Naya notification aane par wave effect dikhega."
            textSize = 13f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(4, 18, 4, 0)
        }
        layout.addView(note, matchWrap())
        setContentView(layout)
    }

    override fun onResume() {
        super.onResume()
        if (::status.isInitialized) {
            status.text = "Overlay permission: ${if (Settings.canDrawOverlays(this)) "ON" else "OFF"}"
        }
    }

    private fun makeButton(label: String, action: () -> Unit): Button {
        return Button(this).apply {
            text = label
            isAllCaps = false
            setOnClickListener { action() }
            val p = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            p.setMargins(0, 8, 0, 8)
            layoutParams = p
        }
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}
