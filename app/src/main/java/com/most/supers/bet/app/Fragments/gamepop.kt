package com.most.supers.bet.app.Fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import com.most.supers.bet.app.R


class gamepop : Fragment() , SurfaceHolder.Callback{
    private lateinit var surfaceView: SurfaceView
    private lateinit var scoreTextView: TextView
    private val timeLimitInSeconds = 60
    private var remainingTimeInSeconds = timeLimitInSeconds
    private var score = 0
    private var ballRadius = 50f
    private var ballX = 0f
    private var ballY = 0f

    private var thread: Thread? = null
    private var running = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gamepop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        surfaceView = view.findViewById(R.id.surfaceView)
        scoreTextView = view.findViewById(R.id.scoreTextView)

        surfaceView.holder.addCallback(this)


    }

    companion object {

        fun newInstance() =
            gamepop()
    }



    @SuppressLint("ResourceAsColor")
    override fun surfaceCreated(p0: SurfaceHolder) {
        val countDownTimer = object : CountDownTimer((timeLimitInSeconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInSeconds = millisUntilFinished.toInt() / 1000
                scoreTextView.text = "Score: $score - Time: $remainingTimeInSeconds"
            }

            override fun onFinish() {
                running = false
                thread = null

                // Add your code to show the final score or restart the game
            }
        }

        countDownTimer.start()

        ballX = (surfaceView.width / 2).toFloat()
        ballY = (surfaceView.height / 2).toFloat()

        thread = Thread {
            running = true

            while (running) {
                if (p0 == null) return@Thread

                val canvas = p0.lockCanvas()

                if (canvas != null) {
//view?.findViewById<SurfaceView>(R.id.surfaceView)?.setBackground(R.drawable.img.toDrawable())
                    canvas.drawColor(Color.GREEN)

                    canvas.drawCircle(ballX, ballY, ballRadius, Paint().apply {
                        color = Color.RED
                        style = Paint.Style.FILL
                    })

                    p0.unlockCanvasAndPost(canvas)
                }

                Thread.sleep(16)
            }
        }

        thread?.start()

        surfaceView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val distance = Math.sqrt(Math.pow((event.x - ballX).toDouble(), 2.0) + Math.pow((event.y - ballY).toDouble(), 2.0)).toFloat()

                if (distance < ballRadius) {
                    ballX = (Math.random() * surfaceView.width).toFloat()
                    ballY = (Math.random() * surfaceView.height).toFloat()

                    score++
                    scoreTextView.text = "Score: $score - Time: $remainingTimeInSeconds"
                }
            }

            true
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        running = false
        thread = null
    }
}