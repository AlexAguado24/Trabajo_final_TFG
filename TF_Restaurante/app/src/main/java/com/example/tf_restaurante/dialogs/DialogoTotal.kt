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
import android.view.Window
import com.example.tf_restaurante.model.Producto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class DialogoTotal : DialogFragment(), View.OnClickListener {

    private lateinit var db: FirebaseDatabase
    private lateinit var adaptador: AdaptadorTotal
    lateinit var aryProduc_tot: ArrayList<ProductoTotal>
    lateinit var aryProBtn: ArrayList<String>
    lateinit var productoTotal: ProductoTotal
    private lateinit var vista: View
    lateinit var recycler_Tot: RecyclerView
    private lateinit var btnPagar: Button
    private lateinit var btnAtras: Button
    private lateinit var totDoub: TextView
    private lateinit var totTex: TextView
    private lateinit var fonTVerde: TextView
    var arrLBtnrec:ArrayList<String> = ArrayList()
    lateinit var acTot: String

    lateinit var nombreUser: String
    var roundoff: Double = 0.0
    private lateinit var auth: FirebaseAuth


    lateinit var arrayCompa: ArrayList<ProductoTotal>
   // lateinit var arrayPro: ArrayList<Producto>

    //DONDE QUIERO RECIBIR
    companion object {
        val args = Bundle()
        fun newInstance(producTot: ArrayList<ProductoTotal>,acum: Double,nombre: String/*,produc:ArrayList<Producto>*/): DialogoTotal {
            val dialogo = DialogoTotal()
            args.putSerializable("producTot", producTot)
          //  args.putSerializable("produc", produc)
            args.putSerializable("acumulador", acum)
            args.putSerializable("nombre", nombre)
            dialogo.arguments = args
            return dialogo
        }
        fun tipoProduc(prodPas:ArrayList<String>){
           args.putSerializable("proBtn", prodPas)
       }

    }


    override fun onAttach(context: Context) {

        arrLBtnrec= this.arguments?.getSerializable("proBtn")  as ArrayList<String>
        arrayCompa = this.arguments?.getSerializable("producTot") as ArrayList<ProductoTotal>
      //  arrayPro = this.arguments?.getSerializable("produc") as ArrayList<Producto>

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
    /*    for (i in arrayPro) {
            Log.v("miro ", i.titulo.toString())

        }*/

        return builder.create()

    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.btn_pagar_t -> {

                if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2"))) {

                fun inter(btnNomRec:String,tit:String,valorSumado:Int){
                    var prodReferen=db.getReference("productos")
                        .child(btnNomRec)
                        .child(tit)
                        .child("stock")
                    prodReferen.setValue(valorSumado)
                }

                    for ((element1, bebComPos) in arrayCompa.zip(arrLBtnrec) ) {
                        inter(bebComPos,element1.titulo.toString(),element1.cantProducto.toString().toInt()+element1.stockTienda.toString().toInt())
                      // println("Elemento 1: ${element1.titulo.toString()} + ${element1.titulo.toString()}  , Elemento 2: $bebComPos,Stock : ${element1.stockTienda.toString()} ")
                    }
                    Snackbar.make(v, "PRODUCTOS CARGADOS!", Snackbar.LENGTH_SHORT).show()

                   // dismiss()

                    //TODO SEGUIR CARGANDO  SI DICE QUE NO EXITPROCESS SI SI , VACIAR LA LISTA
                   exitProcess(0)


                }else{
                    Snackbar.make(v, "Muchas gracias!", Snackbar.LENGTH_SHORT).show()
                    enviarCorreo()
              //      dismiss()
                }

            }
            R.id.btn_atras_t -> {
                dismiss()
            }
        }
    }

    private fun instancias() {

        aryProduc_tot = ArrayList()
        aryProBtn = ArrayList()
        productoTotal = ProductoTotal()
        aryProduc_tot.add(productoTotal)
        aryProduc_tot = arrayCompa

        adaptador = AdaptadorTotal(requireContext(), aryProduc_tot)

        recycler_Tot.adapter = adaptador
        recycler_Tot.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

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
















