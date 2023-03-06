package com.most.supers.bet.app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.most.supers.bet.app.R
import com.most.supers.bet.app.bindingMains


class score : Fragment() {
    private var score: Int = 0

    companion object {
        fun newInstance(score: Int): score {
            val fragment = score()
            val args = Bundle()
            args.putInt("score", score)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt("score")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display the final score on the screen
        val scoreText = view.findViewById<TextView>(R.id.scoreText)
        scoreText.text = score.toString()

        // Add a button listener to play again
        val playAgainButton = view.findViewById<Button>(R.id.playAgainButton)
        playAgainButton.setOnClickListener {
            // Reset the score and load the GameFragment
            score = 0
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(bindingMains.containers.id, gamefootball())
                .commit()
        }
    }
}