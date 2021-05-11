package com.prosjekt.prosjekt_2.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.prosjekt.prosjekt_2.databinding.DialogJoinGameBinding

class JoinGameDialog() : DialogFragment() {

    internal lateinit var listener:GameDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogJoinGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Join game")
                setPositiveButton("Join") { dialog, which ->
                    if(binding.username.text.toString() != ""){
                        listener.onDialogJoinGame(binding.username.text.toString(), binding.gameId.text.toString())
                    }
                }
                setNegativeButton("Cancle") { dialog, which ->
                    dialog.cancel()
                }
                setView(binding.root)
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as GameDialogListener
        } catch (e:ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

}