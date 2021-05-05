package com.prosjekt.prosjekt_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.prosjekt.prosjekt_2.api.GameService
import com.prosjekt.prosjekt_2.api.data.Game
import com.prosjekt.prosjekt_2.databinding.ActivityMainBinding
import com.prosjekt.prosjekt_2.dialogs.CreateGameDialog
import com.prosjekt.prosjekt_2.dialogs.GameDialogListener
import com.prosjekt.prosjekt_2.dialogs.JoinGameDialog

class MainActivity : AppCompatActivity(), GameDialogListener {

    val TAG:String = "MainActivity"
    lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.Companion.context = this.application

        binding.startGameButton.setOnClickListener {
            createNewGame()
        }

        binding.joinGameButton.setOnClickListener {
            joinGame()
        }

    }

    private fun createNewGame(){
        val dlg = CreateGameDialog()
        dlg.show(supportFragmentManager,"CreateGameDialogFragment")
    }

    private fun joinGame(){
        val dlg = JoinGameDialog()
        dlg.show(supportFragmentManager,"JoinGameDialogFragment")
    }

    override fun onDialogCreateGame(player: String) {
        Log.d(TAG,player)
        GameManager.createGame(player)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        Log.d(TAG, "$player $gameId")
        GameManager.joinGame(player, gameId)
    }
}