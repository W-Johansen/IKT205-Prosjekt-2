package com.prosjekt.prosjekt_2

import android.util.Log
import com.prosjekt.prosjekt_2.api.GameService
import com.prosjekt.prosjekt_2.api.data.Game
import com.prosjekt.prosjekt_2.api.data.GameState

object GameManager {

    val TAG:String = "GameManager"

    var player:String? = null
    var state:GameState? = null

    val StartingGameState:GameState = listOf(listOf(0,0,0),listOf(0,0,0),listOf(0,0,0))

    fun createGame(player:String){

        GameService.createGame(player,StartingGameState) { game: Game?, err: Int? ->
            if(err != null){
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
                Log.e(TAG, err.toString())
            } else {
                /// TODO("We have a game. What to do?)
            }
        }

    }



}