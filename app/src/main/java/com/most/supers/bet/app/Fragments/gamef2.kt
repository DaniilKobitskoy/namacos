package com.most.supers.bet.app.Fragments

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.most.supers.bet.app.R
import com.most.supers.bet.app.databinding.FragmentGamef2Binding
import java.lang.Math.*


class gamef2 : Fragment() {

    private lateinit var binding: FragmentGamef2Binding

    private var ball: ImageView? = null
    private var goalpost: ImageView? = null

    private var screenWidth: Float = 0f
    private var screenHeight: Float = 0f

    private var initialX: Float = 0f
    private var initialY: Float = 0f

    private var gravity: Float = 0.1f
    private var velocityX: Float = 0f
    private var velocityY: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamef2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to the ball and goalpost image views
        ball = binding.ballImageView
        goalpost = binding.goalpostImageView

        // Get screen dimensions
        screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
        screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()

        // Set initial ball position
        initialX = ball?.x ?: 0f
        initialY = ball?.y ?: 0f

        // Set up touch listener for ball
        ball?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    velocityX = 0f
                    velocityY = 0f
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    ball?.x = event.rawX - ball!!.width / 2
                    ball?.y = event.rawY - ball!!.height / 2
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val distanceX: Double = event.rawX - initialX .toDouble()
                    val distanceY:Double = initialY - event.rawY .toDouble()
                    val angle = atan2(distanceY, distanceX)
                    velocityX = cos(angle).toFloat() * 10f
                    velocityY = sin(angle).toFloat() * 10f
                    true
                }
                else -> false
            }
        }

        // Start game loop
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                update()
                Handler(Looper.getMainLooper()).postDelayed(this, 16)
            }
        })
    }

    private fun update() {
        // Update ball position based on physics
        velocityY += gravity
        ball?.x = ball?.x?.plus(velocityX) ?: 0f
        ball?.y = ball?.y?.plus(velocityY) ?: 0f

        // Check for collision with goalpost
        if (ball?.x ?: 0f > goalpost?.x ?: 0f && ball?.x ?: 0f < goalpost?.x?.plus(goalpost?.width ?: 0) ?: 0f &&
            ball?.y ?:  0f > goalpost?.y ?: 0f && ball?.y ?: 0f < goalpost?.y?.plus(goalpost?.height ?: 0) ?: 0f) {
            // Ball has entered the goal, reset its position
            ball?.x = initialX
            ball?.y = initialY
            velocityX = 0f
            velocityY = 0f
        }

        // Check for collision with walls
        if (ball?.x ?: 0f <= 0f || ball?.x ?: 0f >= screenWidth - (ball?.width ?: 0)) {
            velocityX *= -1
        }
        if (ball?.y ?: 0f <= 0f || ball?.y ?: 0f >= screenHeight - (ball?.height ?: 0)) {
            velocityY *= -1
        }

    }
}