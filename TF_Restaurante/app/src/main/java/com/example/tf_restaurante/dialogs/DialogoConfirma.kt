package com.example.tf_restaurante.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogoConfirma:DialogFragment() {
    //    private lateinit var listener:OnDialogConfirmListener;

    override fun onAttach(context: Context) {
        super.onAttach(context)
    //    listener =context as OnDialogConfirmListener
    }
   /* interface OnDialogConfirmListener{
        fun OnDialogSelected(seleccion:Boolean)
    }*/




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        var builder:AlertDialog.Builder=AlertDialog.Builder(requireContext())


        //Titulo
        builder.setTitle("Eliminando producto")
        //Mensaje
        builder.setMessage("¿Seguro que quieres Eliminar?")

        //Botones


        builder.setPositiveButton("ok") { _, _ ->
          //  listener.OnDialogSelected(true)
            //Toast.makeText(requireContext(),"Pulsado positive",Toast.LENGTH_SHORT).show()

            print("Entra en TRUE")

            DialogoProducto.OnGetDialConfir(true)

        }
        builder.setNegativeButton("cancelar") { _, _ ->
        //    listener.OnDialogSelected(false)
            //Toast.makeText(requireContext(),"Pulsado negativo",Toast.LENGTH_SHORT).show()
            DialogoProducto.OnGetDialConfir(false)
            print("Entra en FALSE")
        }


        return builder.create()
    }





}