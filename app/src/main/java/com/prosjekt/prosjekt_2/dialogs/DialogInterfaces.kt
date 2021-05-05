package com.prosjekt.prosjekt_2.dialogs

import androidx.fragment.app.DialogFragment

interface GameDialogListener {
    fun onDialogCreateGame(player:String)
    fun onDialogJoinGame(player: String, gameId:String)
}