package com.example.tf_restaurante.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tf_restaurante.R
import com.example.tf_restaurante.adapter.AdaptadorTotal
import com.example.tf_restaurante.model.ProductoTotal
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


import android.content.Intent
import android.util.Log
import com.example.tf_restaurante.model.Producto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import java.util.*
import kotlin.collections.ArrayList

//import javax.mail.*

class DialogoTotal : DialogFragment(), View.OnClickListener {

    private lateinit var db: FirebaseDatabase
    private lateinit var adaptador: AdaptadorTotal
    lateinit var aryProduc_tot: ArrayList<ProductoTotal>
    lateinit var productoTotal: ProductoTotal
    private lateinit var vista: View
    lateinit var recycler_Tot: RecyclerView
    private lateinit var btnPagar: Button
    private lateinit var btnAtras: Button
    private lateinit var totDoub: TextView
    private lateinit var totTex: TextView
    private lateinit var fonTVerde: TextView

    lateinit var acTot: String



    lateinit var nombreUser: String
    var roundoff: Double = 0.0
    private lateinit var auth: FirebaseAuth


    lateinit var arrayCompa: ArrayList<ProductoTotal>
    lateinit var arrayPro: ArrayList<Producto>

    //   TODO
    var tuSaldo = 3000




    //DONDE QUIERO RECIBIR
    companion object {
        fun newInstance(producTot: ArrayList<ProductoTotal>,acum: Double,nombre: String,produc:ArrayList<Producto>): DialogoTotal {
            val dialogo = DialogoTotal()
            val args = Bundle()
            args.putSerializable("producTot", producTot)
            args.putSerializable("produc", produc)
            args.putSerializable("acumulador", acum)
            args.putSerializable("nombre", nombre)
            dialogo.arguments = args
            return dialogo
        }
    }


    override fun onAttach(context: Context) {


        arrayCompa = this.arguments?.getSerializable("producTot") as ArrayList<ProductoTotal>
        arrayPro = this.arguments?.getSerializable("produc") as ArrayList<Producto>
        super.onAttach(context)
        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_total, null)


        acTot = this.arguments?.getDouble("acumulador").toString()
        nombreUser = this.arguments?.getString("nombre").toString();


    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        db = FirebaseDatabase.getInstance("https://restaurante-tfg-default-rtdb.firebaseio.com/")
        var builder = AlertDialog.Builder(requireContext())
        builder.setView(vista)
        btnPagar = vista.findViewById(R.id.btn_pagar_t)
        btnAtras = vista.findViewById(R.id.btn_atras_t)
        totDoub = vista.findViewById(R.id.total_doub_item)
        totTex = vista.findViewById(R.id.total_item)
        fonTVerde = vista.findViewById(R.id.tex_verde)
        recycler_Tot = vista.findViewById(R.id.recycler_total)
        auth = FirebaseAuth.getInstance();

        roundoff = (acTot.toDouble() * 100).roundToInt().toDouble() / 100

        totDoub.setText(roundoff.toString())



        if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2"))) {

            btnPagar.setText("CARGAR")
            totDoub.setText("")
            totTex.setText("")
            fonTVerde.setBackgroundColor(resources.getColor(R.color.cardview_dark_background1))
        }


        return builder.create()

    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.btn_pagar_t -> {





                var prodRec: Int = 0
                if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2"))) {
                    fun comparo(tit_hijo:String) {

                        for (i in arrayPro) {

                            if (i.titulo.equals(tit_hijo)){
                                Log.v("ver0", i.stock.toString() + " Prai")
                                prodRec = i.stock.toString().toInt()
                            }
                        }
                    }


                    fun agregoStock(tit_hijo:String,valor:Int){
                        var prodReferen=db.getReference("bebidas")
                            .child("productos")
                            .child(tit_hijo)
                            .child("stock")
                        prodReferen.setValue(valor+prodRec)

                    }



                    //TODO PONER IQUALS  Y SUMAR STOCK A LOS QUE YA TENIAMOS
                    for (i in arrayCompa) {
                        comparo(i.titulo.toString())
                        agregoStock(i.titulo.toString(),i.cantProducto.toString().toInt())

                        }






                    //TODO CARGAR STOCK DE A 1
               /*     var prodReferen=db.getReference("bebidas")
                        .child("productos")
                        .child("Fanta limon")
                        .child("stock")*/

                    //prodReferen.setValue(300)











                    Snackbar.make(vista, "PRODUCTOS CARGADOS!", Snackbar.LENGTH_SHORT).show()

                }else{
                    Snackbar.make(vista, "Muchas gracias!", Snackbar.LENGTH_SHORT).show()
                    enviarCorreo()

                }
                //  activity?.finish()


            }
            R.id.btn_atras_t -> {
                dismiss()
            }


        }
    }

    private fun instancias() {
        aryProduc_tot = ArrayList()
        productoTotal = ProductoTotal()
        aryProduc_tot.add(productoTotal)
        aryProduc_tot = arrayCompa

        adaptador = AdaptadorTotal(requireContext(), aryProduc_tot)

        recycler_Tot.adapter = adaptador
        recycler_Tot.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }


    @SuppressLint("QueryPermissionsNeeded")
    fun enviarCorreo() {
        var acumulador = ""
        val recipientEmail = nombreUser
        val subject = "Resumen de la cuenta"
        var message = ""
        for (i in arrayCompa) {
            acumulador =
                acumulador + i.titulo.toString() + "              " + i.precio.toString() + "€     X " + i.cantProducto.toString() + "\n"
        }
        message = acumulador + "\n" + "Precio Total__________________________" + roundoff
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Elige una aplicación de correo"))
        }
    }


    override fun onResume() {
        btnPagar.setOnClickListener(this)
        btnAtras.setOnClickListener(this)

        super.onResume()
    }

    override fun onStart() {


        super.onStart()
        instancias()
    }


}
















