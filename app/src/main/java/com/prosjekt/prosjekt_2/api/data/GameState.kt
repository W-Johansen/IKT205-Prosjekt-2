package com.prosjekt.prosjekt_2.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

/*fun toJSONArray(state:GameState):JSONArray{
    val arrayState = JSONArray()

    // :^)
    state.forEach {
        val rowStates = JSONArray()
        it.forEach {
            rowStates.put(it)
        }
        arrayState.put(rowStates)
    }
    return arrayState
}*/

data class GameState(var state: List<List<Int>>){
    fun toJSONArray():JSONArray{
        val arrayState = JSONArray()

        // :^)
        state.forEach {
            val rowStates = JSONArray()
            it.forEach {
                rowStates.put(it)
            }
            arrayState.put(rowStates)
        }
        return arrayState
    }
}

@Parcelize
data class Game(val players:MutableList<String>, val gameId:String, val state:List<List<Int>> ):Parcelable