package com.most.supers.bet.app.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import com.most.supers.bet.app.R
import com.most.supers.bet.app.databinding.FragmentGametictactoeBinding


class gametictactoe : Fragment() {
    private lateinit var board: Array<IntArray>
    private var player = 1
    private lateinit var buttons: Array<Array<Button>>
    lateinit var binding: FragmentGametictactoeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGametictactoeBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_gametictactoe, container, false)
        val resetButton: Button = binding.resetButton
        resetButton.setOnClickListener {
            resetGame()
        }
        val gridLayout: GridLayout = view.findViewById(R.id.gridLayout)
        val numRows = arguments?.getInt(NUM_ROWS_KEY) ?: 3
        val numCols = arguments?.getInt(NUM_COLS_KEY) ?: 3
        board = Array(numRows) { IntArray(numCols) }
        buttons = Array(numRows) { row ->
            Array(numCols) { col ->
                val button = Button(context)
                button.textSize = 40f
                button.setOnClickListener {
                    handleMove(row, col)
                }
                gridLayout.addView(button)
                button
            }
        }
//        resetGame()
        return view
    }

    private fun handleMove(row: Int, col: Int) {
        val button = buttons[row][col]
        button.text = "X"
        if (board[row][col] != 0) {
            Toast.makeText(activity, "Cell already occupied!", Toast.LENGTH_SHORT).show()
            return
        }
        board[row][col] = player
        buttons[row][col].text = if (player == 1) "X" else "O"
        if (checkForWin()) {
            showGameOverDialog("Player $player wins!")
        } else if (checkForDraw()) {
            showGameOverDialog("It's a draw!")
        } else {
            player = if (player == 1) 2 else 1
        }
    }

    private fun checkForWin(): Boolean {
        // Check rows
        for (row in 0..2) {
            if (board[row][0] != 0 && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                return true
            }
        }
        // Check columns
        for (col in 0..2) {
            if (board[0][col] != 0 && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                return true
            }
        }
        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true
        }
        return false
    }

    private fun checkForDraw(): Boolean {
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col] == 0) {
                    return false
                }
            }
        }
        return true
    }

    private fun showGameOverDialog(message: String) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Play Again") { dialog, _ ->
                dialog.dismiss()
                resetGame()
            }
            .setNegativeButton("Quit") { dialog, _ ->
                dialog.dismiss()
                activity?.finish()
            }
        val alert = dialogBuilder.create()
        alert.show()
    }

    private fun resetGame() {
        board = Array(3) { IntArray(3) }
        player = 1
        for (row in 0..2) {
            for (col in 0..2) {
                buttons[row][col].text = ""
            }
        }
    }
    companion object {
        const val NUM_ROWS_KEY = "NUM_ROWS"
        const val NUM_COLS_KEY = "NUM_COLS"

        fun newInstance(numRows: Int, numCols: Int): gametictactoe {
            val fragment = gametictactoe()
            val args = Bundle()
            args.putInt(NUM_ROWS_KEY, numRows)
            args.putInt(NUM_COLS_KEY, numCols)
            fragment.arguments = args
            return fragment
        }
    }
}