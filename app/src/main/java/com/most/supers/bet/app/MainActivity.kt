package com.most.supers.bet.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.most.supers.bet.app.Fragments.gamef2
import com.most.supers.bet.app.Fragments.gamefootball
import com.most.supers.bet.app.Fragments.gamepop
import com.most.supers.bet.app.Fragments.menu
import com.most.supers.bet.app.databinding.ActivityMainBinding
lateinit var bindingMains: ActivityMainBinding
class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMains = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMains.root)
    supportFragmentManager
        .beginTransaction()
        .replace(bindingMains.containers.id, gamef2())
        .commit()


    }
}