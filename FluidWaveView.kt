package com.example.fluidwave

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.sin

class FluidWaveView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var progress = 0f
    private var animator: ValueAnimator? = null

    fun startAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2450L
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                progress = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0f || h <= 0f) return

        val fade = (1f - progress).coerceIn(0f, 1f)
        val centerY = h * (0.58f - 0.22f * progress)
        val amplitude = h * (0.10f + 0.10f * sin(progress * Math.PI).toFloat())
        val shift = progress * w * 0.42f

        val colors = intArrayOf(
            0x0036D9FF,
            0x6636D9FF,
            0xAA4A7DFF,
            0x8845E6D0
        )
        paint.shader = LinearGradient(0f, 0f, w, h, colors, null, Shader.TileMode.CLAMP)
        paint.alpha = (220 * fade).toInt()

        val path = Path().apply {
            moveTo(0f, h)
            lineTo(0f, centerY)
            var x = 0f
            while (x <= w + 20f) {
                val normalized = x / w
                val y = centerY +
                    sin((normalized * 2.5 + progress * 7.0 + shift / w * 2.0) * Math.PI).toFloat() * amplitude
                lineTo(x, y)
                x += 18f
            }
            lineTo(w, h)
            close()
        }
        canvas.drawPath(path, paint)

        paint.shader = null
        paint.color = 0x5548E8FF
        paint.strokeWidth = 3.5f
        paint.style = Paint.Style.STROKE
        val crest = Path().apply {
            var x = 0f
            while (x <= w + 20f) {
                val normalized = x / w
                val y = centerY - 12f +
                    sin((normalized * 2.5 + progress * 7.0 + shift / w * 2.0) * Math.PI).toFloat() * amplitude
                if (x == 0f) moveTo(x, y) else lineTo(x, y)
                x += 18f
            }
        }
        canvas.drawPath(crest, paint)
        paint.style = Paint.Style.FILL
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }
}
