package com.most.supers.bet.app.Fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.most.supers.bet.app.R
import com.most.supers.bet.app.bindingMains
import com.most.supers.bet.app.databinding.FragmentGamefootballBinding
import java.util.*
import kotlin.concurrent.thread


class gamefootball : Fragment() {
    private lateinit var leftGoal: View
    private lateinit var rightGoal: View
    private lateinit var ball: ImageView
    private lateinit var leftScoreText: TextView
    private lateinit var rightScoreText: TextView

    private var leftScore = 0
    private var rightScore = 0

    private var ballXSpeed = 0
    private var ballYSpeed = 0

    private var lastX = 0f
    private var lastY = 0f
    private var isMoving = false

    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gamefootball, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to views
        leftGoal = view.findViewById(R.id.left_goal)
        rightGoal = view.findViewById(R.id.right_goal)
        ball = view.findViewById(R.id.ball)
        leftScoreText = view.findViewById(R.id.left_score_text)
        rightScoreText = view.findViewById(R.id.right_score_text)

        // Set up the ball movement
        ball.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Save the initial touch position
                    lastX = event.x
                    lastY = event.y
                    isMoving = true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Calculate the new position of the ball based on the swipe path
                    ballXSpeed = (event.x - lastX).toInt()
                    ballYSpeed = (event.y - lastY).toInt()

                    // Move the ball to its new position
                    ball.x += ballXSpeed
                    ball.y += ballYSpeed

                    // Save the current touch position
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    // Stop the movement of the ball
                    isMoving = false
                }
            }
            true
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up a timer to periodically update the ball position and check for collisions
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (!isMoving) {
                    return
                }

                // Update the position of the ball based on its speed
                ball.x += ballXSpeed
                ball.y += ballYSpeed

                // Check for collisions with the walls and goals
activity?.runOnUiThread {
    checkGoal()
}



            }
        }, 0, 16)
    }

    private fun checkGoal() {

        // Check if the ball has hit the left wall
        if (ball.x <= leftGoal.right) {
            rightScore++
            rightScoreText.text = rightScore.toString()
            resetBallPosition()
            return
        }

        // Check if the ball has hit the right wall
        if (ball.x + ball.width >= rightGoal.left) {
            leftScore++

            leftScoreText.text = leftScore.toString()
            resetBallPosition()
            return
        }
        if (ball.y <= 0 || ball.y + ball.height >= view?.height ?: 0) {
            ballYSpeed = -ballYSpeed
        }
    }

    private fun resetBallPosition() {
        // Reset the position of the ball to the center of the screen
        ball.x = (view?.width ?: 0) / 2f - ball.width / 2f
        ball.y = (view?.height ?: 0) / 2f - ball.height / 2f

        // Reset the speed of the ball
        ballXSpeed = 0
        ballYSpeed = 0
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the timer when the fragment is destroyed
        timer.cancel()
    }
}