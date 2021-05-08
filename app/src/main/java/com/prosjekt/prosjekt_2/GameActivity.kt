package com.prosjekt.prosjekt_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.prosjekt.prosjekt_2.api.data.Game
import com.prosjekt.prosjekt_2.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    val TAG:String = "GameActivity"
    var isHost:Boolean = false
    var isPlayerTurn:Boolean = false
    var isStarted:Boolean = false

    private lateinit var binding:ActivityGameBinding


    private lateinit var localGame:Game

    private lateinit var mainHandler:Handler

    private val updateTask = object : Runnable {
        override fun run(){
            //Oppdateres hvert sekund
            pollUpdates()
            mainHandler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isHost = intent.getBooleanExtra("isHost", false)

        localGame = GameManager._game

        mainHandler = Handler(Looper.getMainLooper())

        binding.player1.text = "%1s: X".format(localGame.players[0])

        if (isHost)
            isPlayerTurn = true

        if (localGame.players.size == 2) {
            binding.player2.text = "%1s: O".format(localGame.players[1])
            isStarted = true
        } else {
            binding.turnInd.text = "GameID: %1s".format(localGame.gameId)
            binding.player2.text = "Waiting for opponent..."
        }

        updateTurnDisplay()
        mainHandler.post(updateTask)
    }

    fun pollUpdates(){
        Log.d(TAG, "Polling game...")
        GameManager.pollGame(localGame.gameId){game: Game? ->
            when {
                // Venter på at en motstander skal joine
                localGame.players.size != game!!.players.size && !isStarted -> {
                    binding.player2.text = "%1s: O".format(game.players[1])
                    mainHandler.removeCallbacks(updateTask)
                    isStarted = true
                    localGame.players = game.players
                    updateTurnDisplay()
                }

                localGame.state != game.state -> {
                    // State er endret
                    if (isHost && GameManager.turn % 2 == 0){
                        isPlayerTurn = true
                    } else if (!isHost && GameManager.turn % 2 == 0){
                        isPlayerTurn = false
                    }

                    if(isHost && GameManager.turn % 2 != 0){
                        isPlayerTurn = false
                    } else if (!isHost && GameManager.turn % 2 != 0){
                        isPlayerTurn = true
                    }
                    localGame.state = game.state
                }

                else -> {
                    // Ingen endring
                    localGame = game
                }
            }
        }
    }

    fun updateBoardDisplay(){

    }

    fun updateTurnDisplay(){
        if(localGame.players.size == 2){
            if (isPlayerTurn){
                binding.turnInd.text = "Your turn!"
            } else {
                binding.turnInd.text = "Opponents turn!"
            }
        }
    }

}