package com.prosjekt.prosjekt_2.dialogs

interface GameDialogListener {
    fun onDialogCreateGame(player:String)
    fun onDialogJoinGame(player: String, gameId:String)
}