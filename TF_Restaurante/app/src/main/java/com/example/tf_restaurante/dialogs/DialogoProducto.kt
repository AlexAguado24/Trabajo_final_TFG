package com.example.tf_restaurante.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.tf_restaurante.MainActivity
import com.example.tf_restaurante.R
import com.example.tf_restaurante.model.Producto
import com.google.android.material.snackbar.Snackbar


class DialogoProducto :DialogFragment(),View.OnClickListener {



    companion object{
        fun newInstance(producto: Producto): DialogoProducto {
            val args = Bundle()
            args.putSerializable("producto", producto)
            val fragment = DialogoProducto()
            fragment.arguments = args
            return fragment
        }
    }



    private lateinit var vista: View
    private lateinit var img: ImageView
    private lateinit var producto:  TextView
    private lateinit var precio:  TextView
    private lateinit var arriba: Button
    private lateinit var abajo: Button
    private lateinit var ok: Button
    private lateinit var back: Button
    private lateinit var editCant: TextView
    var cont=0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //vista = LayoutInflater.from(context).inflate(R.layout.dialogo_producto,null)
        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_producto,null)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(requireContext())

        builder.setView(vista)

        instancias()







        return builder.create()
    }
    fun instancias() {

        img = vista.findViewById(R.id.img_dialogo)
        producto = vista.findViewById(R.id.text_producto)
        precio = vista.findViewById(R.id.text_precio)
        arriba = vista.findViewById(R.id.btn_arriba)
        abajo = vista.findViewById(R.id.btn_abajo)
        ok = vista.findViewById(R.id.btn_ok)
        back = vista.findViewById(R.id.btn_cancel)
        editCant = vista.findViewById(R.id.edit_can_pro)

    }

    override fun onStart() {

        var productoGr =this.arguments?.getSerializable("producto")as Producto
        precio.setText("Precio: " + productoGr.precio!!.toDouble())
        Glide.with(requireContext()).
        load(productoGr.imagen)
            .into(img)

        producto.setText(productoGr.titulo!!.toString())
        super.onStart()
    }

    override fun onResume() {


      //  android:id="@+id/btn_arriba"
        //.btnBebida.setOnClickListener(this)
        arriba.setOnClickListener(this)
        abajo.setOnClickListener(this)
        ok.setOnClickListener(this)
        back.setOnClickListener(this)
        super.onResume()



    }

    override fun onClick(v: View?) {


        when (v!!.id) {
            R.id.btn_arriba -> {
                //producto.setText(productoGr.titulo!!.toString())
                cont += 1
                editCant.setText((cont).toString())

                //TODO  ############################# Falta restar del Stock y si no hay mas de la seleccionada que indique a traves de un cuadro
                //TODO ############################## no hay mas ,la cantidad que hay es =5 quieres comprarlas? si, no
                //TODO ###########################    Si si ,resta stock y muestra snackbar producto agotado
            }
            R.id.btn_abajo -> {
                cont-= 1
                if (editCant.text.toString().toInt() <= 0) {
                       Snackbar.make(v, "No es posible", Snackbar.LENGTH_SHORT).show()
                    cont= 0
                } else {

                    editCant.setText((cont).toString())
                }

            }
            R.id.btn_ok -> {


            }
            R.id.btn_cancel -> {


            }
        }

    }

}