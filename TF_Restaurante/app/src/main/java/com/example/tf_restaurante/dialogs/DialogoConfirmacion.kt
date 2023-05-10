package com.example.tf_restaurante.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class DialogoConfirmacion : DialogFragment() {



    private lateinit var listener: OnDialogoConfirmListener;
    var funcionNula: ((Boolean) -> Unit)? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnDialogoConfirmListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Builder --> creador
        var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        // titulo
        builder.setTitle("Titulo del cuadro de confirmación")

        // mensaje
        builder.setMessage("¿Estás seguro que quieres continuar el proceso?")

        // botones
        builder.setPositiveButton("OK") { dialogInterface, i ->
            funcionNula?.invoke(true)

        }
        builder.setNegativeButton("CANCELAR") { dialogInterface, i ->
            funcionNula?.invoke(false)

        }

        return builder.create()
    }

    override fun onDetach() {
        super.onDetach()
    }

    interface OnDialogoConfirmListener {
        fun onDialogoSelected(seleccionado: Boolean)
    }



}