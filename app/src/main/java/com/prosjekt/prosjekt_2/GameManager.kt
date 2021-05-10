package com.prosjekt.prosjekt_2

import android.util.Log
import com.prosjekt.prosjekt_2.api.GameService
import com.prosjekt.prosjekt_2.api.data.Game
import com.prosjekt.prosjekt_2.api.data.GameState

typealias PollServiceCallback = (game:Game? ) -> Unit

object GameManager {

    val TAG:String = "GameManager"

    lateinit var mainActivity:MainActivity

    var _player:String? = null
    var state:GameState? = null
    var turn:Int = 0
        get() {
            var t:Int = 0
            _game.state.forEach {
                it.forEach {
                    if (it != 0)
                        t++
                }
            }
            turn = t // Nødvendig?
            return t
        }

    val StartingGameState = GameState(mutableListOf(mutableListOf(0,0,0),mutableListOf(0,0,0),mutableListOf(0,0,0)))

    // Brude kaskje være null?
    var _game:Game = Game(mutableListOf(""),"",  mutableListOf(mutableListOf(0,0,0),mutableListOf(0,0,0),mutableListOf(0,0,0)))

    fun createGame(player:String){

        GameService.createGame(player,StartingGameState) { game: Game?, err: Int? ->
            if(err != null){
                Log.e(TAG, "Error creating game, error code: $err")
            } else {
                Log.d(TAG, "Created game with gameID: " + game!!.gameId)
                _game.players = game.players
                _game.gameId = game.gameId
                _game.state = StartingGameState.state
                _player = player
                mainActivity.beginActivity(GameActivity::class.java, true)
            }
        }

    }

    fun joinGame(player:String, gameId:String){
        GameService.joinGame(player,gameId) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e(TAG, "Error joining game, error code: $err")
            } else {
                Log.d(TAG, "Joined game: " + game!!.gameId + "\n Players: " + game.players)
                _game.players = game.players
                _game.gameId = game.gameId
                _game.state = game.state
                _player = player
                mainActivity.beginActivity(GameActivity::class.java, false)
            }
        }
    }

    fun updateGame(gameId:String, gameState:GameState, callback:PollServiceCallback){
        GameService.updateGame(gameId, gameState) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e(TAG, "Error updating game, error code: $err")
            } else {
                Log.d(TAG, "Updated game: " + game!!.gameId)
                callback(game)
            }
        }
    }

    fun pollGame(gameId:String, callback:PollServiceCallback){
        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e(TAG, err.toString())
            } else {
                Log.d(TAG, "Polled game: " + game!!.gameId + "\n Players: " + game.players)
                _game.players = game.players
                _game.gameId = game.gameId
                _game.state = game.state
                callback(game)
            }
        }
    }

    fun restartGame(gameId:String, callback:PollServiceCallback){
        GameService.updateGame(gameId, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e(TAG, "Error restarting game, error code: $err")
            } else {
                Log.d(TAG, "Restarted game: " + game!!.gameId)
                callback(game)
            }
        }
    }

}