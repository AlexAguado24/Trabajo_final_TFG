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
import com.example.tf_restaurante.adapter.AdaptadorProductos
import com.example.tf_restaurante.model.Producto
import com.example.tf_restaurante.model.ProductoTotal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.roundToInt


class DialogoProducto : DialogFragment(), View.OnClickListener {

    lateinit var listenerT: OnProductoTotal
    private lateinit var auth: FirebaseAuth;
    lateinit var btnokSel:String
  //  private lateinit var adaptadorPr: AdaptadorProductos
    var arrayProd: ArrayList<Producto> = ArrayList()
    var aryPrBtn: ArrayList<String> = ArrayList()

    interface OnProductoTotal {
        fun onProductoTotal(productoTotal: ProductoTotal)

        fun onEnvBtnConfir(envBtn: String)


    }

    companion object {
        val args = Bundle()
        fun newInstance(producto: Producto): DialogoProducto {
            args.putSerializable("producto", producto)
            val fragment = DialogoProducto()
            fragment.arguments = args
            return fragment
        }
        fun btnOkSelec (btnSel:String){
            args.putSerializable("selBtn", btnSel)
        }
    }


    private lateinit var vista: View
    private lateinit var img: ImageView
    private lateinit var producto: TextView
    private lateinit var precio: TextView
    private lateinit var arriba: Button
    private lateinit var abajo: Button
    private lateinit var ok: Button
    private lateinit var back: Button
    private lateinit var editCant: TextView
    var cont = 0
    private var valorGral: Double = 0.0
    private lateinit var arrayPrecio: ArrayList<Double>
    var acumTot = 0.0
    lateinit var productoGr: Producto
    var totalFinal:Double = 0.0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_producto, null)
        listenerT = requireContext() as OnProductoTotal

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
        editCant = vista.findViewById(R.id.edit_can_pro)




    }

    override fun onStart() {

        productoGr = this.arguments?.getSerializable("producto") as Producto
        btnokSel= this.arguments?.getString("selBtn").toString()
        valorGral = productoGr.precio!!.toDouble()




        if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2"))) {
            Glide.with(requireContext()).load(productoGr.imagen).apply(RequestOptions.circleCropTransform()).into(img)
            producto.setText(productoGr.titulo!!.toString())
            ok.setText("cargar")
        }else{

            precio.setText("Precio: " + valorGral)
            //   Glide.with(requireContext()).load(productoGr.imagen).into(img)
            Glide.with(requireContext()).load(productoGr.imagen).apply(RequestOptions.circleCropTransform()).into(img)

            producto.setText(productoGr.titulo!!.toString())


        }

        super.onStart()
    }

    override fun onResume() {

        arriba.setOnClickListener(this)
        abajo.setOnClickListener(this)
        ok.setOnClickListener(this)
        back.setOnClickListener(this)
        super.onResume()


    }

    override fun onClick(v: View?) {


        when (v!!.id) {
            R.id.btn_arriba -> {
                cont += 1
                editCant.setText((cont).toString())


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

                if (cont > 0) {
                    arrayPrecio = ArrayList()
                    arrayPrecio.add((valorGral) * cont.toDouble())
                    for (i in arrayPrecio) {
                        acumTot += i
                    }

                    var prodTot: ProductoTotal
                 //   val redondeo = String.format("%.2f", acumTot).toDouble()
                   var roundoff = (acumTot * 100).roundToInt().toDouble() / 100

                 //     totalFinal=totalFinal+roundoff
                 //   Snackbar.make(vista, "Total = ${totalFinal} ", Snackbar.LENGTH_SHORT).show()
                    prodTot = (ProductoTotal(
                        productoGr.imagen,
                        cont,
                        productoGr.titulo,
                        productoGr.precio,
                        roundoff,
                        productoGr.stock

                      //  totalFinal.toString()

                    ))

                    listenerT.onProductoTotal(prodTot)
                   // Log.v("Total",totalFinal.toString())
                    // Log.v("salida", producto!!.precio.toString())
                   // Snackbar.make(vista, "Total = ${redondeo} ", Snackbar.LENGTH_SHORT).show()


//TODO
                    if (auth.currentUser!!.email.equals("usuarioadmin@gmail.com") && (auth.currentUser!!.uid.equals("V64nPiwdlUhIIk7K8xt7upDQTsc2"))){
                        //SI SOY ADMIN RECIVO DESDE EL SECONDA. EL VALOR DEL BTN ,COMIDA ETC Y SE LO VUELVO A ENVIAR AL SECONDA. CUANDO CONFIRMA LA COMPRA
                        //NO SE LO PUEDE PASAR AL SECOND PORQUE SOLO RECIBE 1 SOLO VALOR Y LO PISA,POR ESO SE LO ENVIO AL ADAPTADOR
                        //ENVIO DE A 1 Y AL SECONDA.EL SECOND LLAMA AL LA FUN DEL ADAPTADOR Y LO AGREGA

                        listenerT.onEnvBtnConfir(btnokSel)
                     //   Log.v("Array", btnokSel)
             //           adaptadorPr.agreBtn(btnokSel)

                    }



                    dismiss()
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