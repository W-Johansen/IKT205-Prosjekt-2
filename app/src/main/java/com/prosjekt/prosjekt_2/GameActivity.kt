package com.prosjekt.prosjekt_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import com.prosjekt.prosjekt_2.api.data.Game
import com.prosjekt.prosjekt_2.api.data.GameState
import com.prosjekt.prosjekt_2.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    val TAG:String = "GameActivity"
    var isHost:Boolean = false
    var isPlayerTurn:Boolean = false
    var isStarted:Boolean = false

    var rowCount = mutableListOf<Int>(0,0,0)
    var colCount = mutableListOf<Int>(0,0,0)
    var diaCount = mutableListOf<Int>(0,0,0)
    var opsCount = mutableListOf<Int>(0,0,0)

    private lateinit var binding:ActivityGameBinding

    lateinit var btnList:List<List<Button>>

    private lateinit var localGame:Game

    private lateinit var mainHandler:Handler

    private val updateTask = object : Runnable {
        override fun run(){
            pollUpdates()
            mainHandler.postDelayed(this, 500)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnList = listOf(listOf(binding.b00, binding.b01, binding.b02), listOf(binding.b10, binding.b11, binding.b12), listOf(binding.b20, binding.b21, binding.b22))

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
        updateBoardDisplay()
        mainHandler.post(updateTask)
    }

    fun btnEvent(view:View){
        val btnSelect = view as Button

        // Finnes garra en klokere måte
        when(btnSelect.id){
            binding.b00.id->{ changeState(0,0)}
            binding.b01.id->{ changeState(0,1)}
            binding.b02.id->{ changeState(0,2)}

            binding.b10.id->{ changeState(1,0)}
            binding.b11.id->{ changeState(1,1)}
            binding.b12.id->{ changeState(1,2)}

            binding.b20.id->{ changeState(2,0)}
            binding.b21.id->{ changeState(2,1)}
            binding.b22.id->{ changeState(2,2)}
        }
    }

    fun pollUpdates(){
        Log.d(TAG, "Polling game...")
        GameManager.pollGame(localGame.gameId){game: Game? ->
            when {
                // Venter på at en motstander skal joine
                localGame.players.size != game!!.players.size && !isStarted -> {
                    binding.player2.text = "%1s: O".format(game.players[1])

                    isStarted = true
                    localGame.players = game.players

                    updateTurnDisplay()

                    mainHandler.removeCallbacks(updateTask)
                }

                localGame.state != game.state -> {
                    isPlayerTurn = true
                    localGame.state = game.state

                    updateBoardDisplay()
                    updateTurnDisplay()

                    mainHandler.removeCallbacks(updateTask)

                    if (checkWin() != 0){
                        isPlayerTurn = false
                    }
                }

                else -> {
                    // Ingen endring
                    localGame = game
                }
            }
        }
    }

    fun updateBoardDisplay(){
        for (i in 0..2){
            for (j in 0..2){
                btnList[i][j].text = if(localGame.state[i][j] == 1) "X" else if(localGame.state[i][j] == 2) "O" else ""
            }
        }
    }

    fun updateTurnDisplay(){
        if(localGame.players.size == 2){
            if (isPlayerTurn){
                binding.turnInd.text = "Your turn!"
            } else {
                binding.turnInd.text = "Opponents turn!"
            }
        }
        if (checkWin() != 0){
            when {
                checkWin() == 1 -> {
                    if(isHost){
                        binding.turnInd.text = "You win!"
                    } else {
                        binding.turnInd.text = "You lose!"
                    }
                }

                checkWin() == 2 -> {
                    if(isHost){
                        binding.turnInd.text = "You lose!"
                    } else {
                        binding.turnInd.text = "You win!"
                    }
                }

                checkWin() == 3 -> {
                    binding.turnInd.text = "Draw!"
                }
            }
        }
    }

    fun changeState(x:Int, y:Int){
        if(localGame.state[x][y] == 0 && isPlayerTurn){
            localGame.state[x][y] = if(isHost) 1 else 2
            isPlayerTurn = false

            updateBoardDisplay()
            updateTurnDisplay()
            updateGame()
        }
    }

    fun updateGame(){
        GameManager.updateGame(localGame.gameId, GameState(localGame.state)){
            if (checkWin() == 0)
                mainHandler.post(updateTask)
        }
    }

    fun checkWin():Int{
        // --- Draw ---
        var t:Int = 0
        localGame.state.forEach {
            it.forEach {
                if (it != 0)
                    t++
            }
        }
        if (t >= 9){
            return 3
        }

        for (p in 1..2){

            rowCount = mutableListOf<Int>(0,0,0)
            colCount = mutableListOf<Int>(0,0,0)
            diaCount = mutableListOf<Int>(0,0,0)
            opsCount = mutableListOf<Int>(0,0,0)

            for (x in 0..2){
                for (y in 0..2){
                    if (localGame.state[x][y] == p){
                        rowCount[x] += 1
                        colCount[y] += 1
                        if (x == y) {
                            diaCount[x] += 1
                        }
                        if (x + y + 1 == 3){
                            opsCount[x] += 1
                        }
                    }
                }
            }

            // --- row ---
            rowCount.forEach {
                if (it == 3)
                    return p
            }

            // --- column ---
            colCount.forEach {
                if (it == 3)
                    return p
            }

            // --- diagonal ---
            if(diaCount.sum() == 3){
                return p
            }

            // --- opposite ---
            if(opsCount.sum() == 3){
                return p
            }
        }

        //Om ingen vinner
        return 0
    }
}