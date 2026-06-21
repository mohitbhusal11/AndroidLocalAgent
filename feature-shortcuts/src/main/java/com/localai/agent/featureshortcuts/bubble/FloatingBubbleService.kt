package com.localai.agent.featureshortcuts.bubble

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.localai.agent.core.util.Constants
import com.localai.agent.featureshortcuts.R

class FloatingBubbleService : Service() {

    private var windowManager: WindowManager? = null
    private var bubbleView: View? = null
    private var params: WindowManager.LayoutParams? = null

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 200
        }

        bubbleView = LayoutInflater.from(this).inflate(R.layout.bubble_layout, null)
        val bubble = bubbleView!!.findViewById<ImageView>(R.id.bubble_icon)

        bubble.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            private var isDragging = false

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params!!.x
                        initialY = params!!.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        isDragging = false
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params!!.x = initialX + (event.rawX - initialTouchX).toInt()
                        params!!.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager?.updateViewLayout(bubbleView, params)
                        isDragging = true
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        if (!isDragging) openAssistant(Constants.ACTION_OPEN_ASSISTANT)
                        return true
                    }
                }
                return false
            }
        })

        bubble.setOnLongClickListener {
            openAssistant(Constants.ACTION_VOICE)
            true
        }

        windowManager?.addView(bubbleView, params)
    }

    private fun openAssistant(action: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            this.action = action
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        bubbleView?.let { windowManager?.removeView(it) }
        super.onDestroy()
    }

    companion object {
        fun start(context: android.content.Context) {
            context.startService(Intent(context, FloatingBubbleService::class.java))
        }

        fun stop(context: android.content.Context) {
            context.stopService(Intent(context, FloatingBubbleService::class.java))
        }
    }
}
