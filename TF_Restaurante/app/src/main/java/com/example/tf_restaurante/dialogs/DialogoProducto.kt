package com.example.tf_restaurante.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tf_restaurante.R
import com.example.tf_restaurante.model.Producto
import com.example.tf_restaurante.model.ProductoTotal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.roundToInt


class DialogoProducto : DialogFragment(), View.OnClickListener {

    private lateinit var db: FirebaseDatabase
    lateinit var listenerT: OnProductoTotal
    private lateinit var auth: FirebaseAuth;
    lateinit var btnokSel: String
    private lateinit var vista: View
    private lateinit var img: ImageView
    private lateinit var producto: TextView
    private lateinit var precio: TextView
    private lateinit var arriba: Button
    private lateinit var abajo: Button
    private lateinit var ok: Button
    private lateinit var back: Button
    private lateinit var elim: Button
    private lateinit var editCant: TextView
    var cont = 0
    private var valorGral: Double = 0.0
    private lateinit var arrayPrecio: ArrayList<Double>
    var acumTot = 0.0
    lateinit var productoGr: Producto
    var botSel:Boolean=true



    //  private lateinit var mayor: ImageButton

    interface OnProductoTotal {
        fun onProductoTotal(productoTotal: ProductoTotal)

        fun onEnvBtnConfir(envBtn: String)

        fun onRecreo()




    }

    companion object {
        val args = Bundle()
        fun newInstance(producto: Producto): DialogoProducto {
            args.putSerializable("producto", producto)
            val fragment = DialogoProducto()
            fragment.arguments = args
            return fragment
        }

        fun btnOkSelec(btnSel: String) {
            args.putSerializable("selBtn", btnSel)
        }
        fun onGetDialConfir(seleccion: Boolean) {
            args.putSerializable("selBool", seleccion)
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_producto, null)
        listenerT = requireContext() as OnProductoTotal
        db = FirebaseDatabase.getInstance("https://restaurante-tfg-default-rtdb.firebaseio.com/")
        //   adaptadorPr = AdaptadorProductos(context, aryProductos, supportFragmentManager,aryPrBtn)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(requireContext())
        builder.setView(vista)
        instancias()
        auth = FirebaseAuth.getInstance();
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
        elim = vista.findViewById(R.id.btn_eliminar)
        editCant = vista.findViewById(R.id.edit_can_pro)


    }

    override fun onStart() {
    //    prducTot = this.arguments?.getSerializable("selProd") as ProductoTotal
        productoGr = this.arguments?.getSerializable("producto") as Producto
        btnokSel = this.arguments?.getString("selBtn").toString()
        botSel = this.arguments?.getBoolean("selBool") as Boolean

        valorGral = productoGr.precio!!.toDouble()




        if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2" ))) {

            Glide.with(requireContext()).load(productoGr.imagen)
                .apply(RequestOptions.circleCropTransform()).into(img)
            producto.setText(productoGr.titulo!!.toString())
            elim.visibility=View.VISIBLE

        } else {



            precio.setText("Precio: " + valorGral)
            //   Glide.with(requireContext()).load(productoGr.imagen).into(img)
            Glide.with(requireContext()).load(productoGr.imagen)
                .apply(RequestOptions.circleCropTransform()).into(img)

            producto.setText(productoGr.titulo!!.toString())

        }

        super.onStart()
    }

    override fun onResume() {

        arriba.setOnClickListener(this)
        abajo.setOnClickListener(this)
        ok.setOnClickListener(this)
        back.setOnClickListener(this)
        elim.setOnClickListener(this)
     //   mayor.setOnClickListener(this)

        super.onResume()


    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.btn_eliminar -> {

                //TODO CUADRO DE DIALOGO

             var notificacion = Snackbar.make(vista,"¿Seguro que desea eliminar? " ,Snackbar.LENGTH_INDEFINITE)
                notificacion.setAction("Confirmar") {
                    var prodReferen = db.getReference("productos")
                        .child(btnokSel)
                        .child(productoGr.titulo.toString())
                    prodReferen.setValue(null)

                    dismiss()
                    listenerT.onRecreo()
                }
                notificacion.show()











            }

            R.id.btn_arriba -> {
                cont += 1
                editCant.setText((cont).toString())

                //  Log.v("contador 0 ",cont.toString())
            }
            R.id.btn_abajo -> {
                cont -= 1
                if (editCant.text.toString().toInt() <= 0) {
                    Snackbar.make(v, "No es posible", Snackbar.LENGTH_SHORT).show()
                    cont = 0
                } else {

                    editCant.setText((cont).toString())
                }

            }
            R.id.btn_ok -> {
                Log.v("contador ", cont.toString())

                if (cont > 0) {


                    fun cargoDial(){
                //### ME CARGA EN EL CUADRO DE DIALOGO SOLO SI EL VALOR SELEC ES INFERIOR AL VALOR DE STOCK
                        arrayPrecio = ArrayList()
                        arrayPrecio.add((valorGral) * cont.toDouble())
                        for (i in arrayPrecio) {
                            acumTot += i
                        }

                        var prodTot: ProductoTotal
                        //redondeo con el roundoff
                        var roundoff =
                            (acumTot * 100).roundToInt().toDouble() / 100

                        prodTot = (ProductoTotal(
                            productoGr.imagen,
                            cont,
                            productoGr.titulo,
                            productoGr.precio,
                            roundoff,
                            productoGr.stock


                        ))
                        if (prodTot.cantProducto.toString().toInt()>0) {
                            listenerT.onProductoTotal(prodTot)
                        }

                        cont=0

                //### FIN DE  ME CARGA EN EL CUADRO DE DIALOGO SOLO SI EL VALOR SELEC ES INFERIOR AL VALOR DE STOCK
                    }





                    if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2" ))) {

                        //SI SOY ADMIN RECIVO DESDE EL SECONDA. EL VALOR DEL BTN ,COMIDA ETC Y SE LO VUELVO A ENVIAR AL SECONDA. CUANDO CONFIRMA LA COMPRA
                        //NO SE LO PUEDE PASAR AL SECOND PORQUE SOLO RECIBE 1 SOLO VALOR Y LO PISA,POR ESO SE LO ENVIO AL ADAPTADOR
                        //ENVIO DE A 1 Y AL SECONDA.EL SECOND LLAMA AL LA FUN DEL ADAPTADOR Y LO AGREGA

                        listenerT.onEnvBtnConfir(btnokSel)
                        //   Log.v("Array", btnokSel)
                        //           adaptadorPr.agreBtn(btnokSel)
                        //##LLAMO A LA FUN QUE CARGA LOS LOS ITEMS
                        cargoDial()
                        dismiss()
                        //TODO TIENE QUE HABER UN DISSMIS PARA EL ADMIN Y OTRO PARA EL USUARIO-NO SE PUEDE PONER AL FINAL ANTES DE TERMINAR LA PULSACIÓN DEL BTN PORQUE PORQUE NO COMPILA
                    } else {


            //## Traigo el valor del stock en tiempo real
                            db.getReference("productos")
                                .child(btnokSel)
                                .child(productoGr.titulo.toString())
                                .child("stock")
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        var stockI = snapshot.getValue(Int::class.java)
            //## FIN Traigo el valor del stock en tiempo real
                                        Log.v("Prue", stockI.toString())

                    //#RESTO EL VALOR QUE ME PASA DE STOCK POR LA CANTIDAD QUE COSUMO

                                        fun inter( btnNomRec: String,tit: String,  valorSumado: Int   ) {

                                            var prodReferen = db.getReference("productos")
                                                .child(btnNomRec)
                                                .child(tit)
                                                .child("stock")
                                            prodReferen.setValue(valorSumado)
                                        }
                                        Log.v("contador1 ", cont.toString())
                    //#RESTO EL VALOR QUE ME PASA DE STOCK POR LA CANTIDAD QUE COSUMO

                            //##   VALIDO SI HAY PRODUCTOS EN STOCK
                                        if ((cont <= stockI!!) && (stockI > 0)) {

                                            inter(  btnokSel, productoGr.titulo.toString(),(stockI - cont) )

                                            //##LLAMO A LA FUN QUE CARGA LOS LOS ITEMS
                                            cargoDial()
                                            dismiss()
                                            //TODO TIENE QUE HABER UN DISSMIS PARA EL ADMIN Y OTRO PARA EL USUARIO-NO SE PUEDE PONER AL FINAL ANTES DE TERMINAR LA PULSACIÓN DEL BTN PORQUE PORQUE NO COMPILA




                                        } else if (stockI == 0 && cont > 0) {
                                            Snackbar.make(vista,"No hay de este producto disponible", Snackbar.LENGTH_SHORT ).show()
                                        } else if (cont > stockI) {
                                            Snackbar.make(vista,"Supera el stock, usted puede seleccionar ${stockI} de este producto",Snackbar.LENGTH_SHORT).show()
                                        }
                            //##  FIN VALIDO SI HAY PRODUCTOS EN STOCK

                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        //ERROR EN LA COMUNICACIÓN
                                    }
                                })
                    }
                } else {
                    Snackbar.make(vista, "Ingrese un valor mayor que 1! ", Snackbar.LENGTH_SHORT).show()
                }



            }
            R.id.btn_cancel -> {
                dismiss()

            }
        }

    }


}

