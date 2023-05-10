package com.example.tf_restaurante.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.tf_restaurante.R
import com.example.tf_restaurante.model.Producto


class DialogoProducto :DialogFragment() {



    private lateinit var vista: View
    private lateinit var img: ImageView
    private lateinit var producto:  TextView
    private lateinit var precio:  TextView
    private lateinit var arriba: Button
    private lateinit var abajo: Button
    private lateinit var editCant: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_producto,null)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireContext())

        builder.setView(vista)

        return builder.create()
    }
    fun instancias() {

        img = vista.findViewById(R.id.img_dialogo)
        producto = vista.findViewById(R.id.text_producto)
        precio = vista.findViewById(R.id.text_precio)
        arriba = vista.findViewById(R.id.btn_arriba)
        abajo = vista.findViewById(R.id.btn_abajo)
        editCant = vista.findViewById(R.id.edit_can_pro)
    }

    override fun onStart() {
        super.onStart()
        instancias()
    }

    override fun onResume() {
        super.onResume()


    }

}