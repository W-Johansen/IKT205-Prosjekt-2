package com.prosjekt.prosjekt_2.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

typealias GameState = List<List<Int>>

fun toJSONArray(state:GameState):JSONArray{
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

@Parcelize
data class Game(val players:MutableList<String>, val gameId:String, val state:GameState ):Parcelable