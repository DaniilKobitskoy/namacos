package com.most.supers.bet.app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.most.supers.bet.app.R
import com.most.supers.bet.app.bindingMains
import com.most.supers.bet.app.databinding.FragmentMenuBinding


class menu : Fragment() {


    lateinit var binding: FragmentMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {

            var game1 = gamepop()
            var next = requireFragmentManager().beginTransaction()
            next.replace(bindingMains.containers.id, game1).commit()


        }

        binding.button10.setOnClickListener {

            var game2 = gametictactoe()
            var next = requireFragmentManager().beginTransaction()
            next.replace(bindingMains.containers.id, game2).commit()


        }
    }

    companion object {

        fun newInstance() =
            menu()
    }
}