package com.example.tf_restaurante.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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

class DialogoTotal : DialogFragment(), View.OnClickListener {


    private lateinit var adaptador: AdaptadorTotal
    lateinit var aryProduc_tot: ArrayList<ProductoTotal>
    lateinit var productoTotal: ProductoTotal
    private lateinit var vista: View
    lateinit var recycler_Tot: RecyclerView
    private lateinit var btnPagar:Button
    private lateinit var btnAtras:Button
    private lateinit var totDoub:TextView
    lateinit var acTot:String



    lateinit var arrayCompa:ArrayList<ProductoTotal>

   //   TODO
    var tuSaldo = 3000






    //DONDE QUIERO RECIBIR
    companion object{
        fun newInstance(producTot: ArrayList<ProductoTotal>, acum: Double): DialogoTotal {
            val dialogo = DialogoTotal()
            val args = Bundle()
            args.putSerializable("producTot", producTot)
            args.putSerializable("acumulador", acum)

            dialogo.arguments = args
            return dialogo
        }
    }






    override fun onAttach(context: Context) {


        arrayCompa=this.arguments?.getSerializable("producTot")as ArrayList<ProductoTotal>
        super.onAttach(context)
        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_total, null)


       acTot=this.arguments?.getDouble("acumulador").toString()

    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireContext())
        builder.setView(vista)
        btnPagar=vista.findViewById(R.id.btn_pagar_t)
        btnAtras=vista.findViewById(R.id.btn_atras_t)
        totDoub=vista.findViewById(R.id.total_doub_item)
        recycler_Tot=vista.findViewById(R.id.recycler_total)


        var roundoff=(acTot.toDouble() * 100).roundToInt().toDouble() / 100

        totDoub.setText(roundoff.toString())


        return builder.create()

    }
    override fun onClick(v: View?) {
        when(v!!.id) {

            R.id.btn_pagar_t->{


                Snackbar.make(vista,"Muchas gracias!",Snackbar.LENGTH_SHORT).show()

            }
            R.id.btn_atras_t->{
                dismiss()
            }


        }
    }

    private fun instancias() {
        aryProduc_tot=ArrayList()
        productoTotal=ProductoTotal()
        aryProduc_tot.add(productoTotal)
        aryProduc_tot=arrayCompa

        adaptador = AdaptadorTotal(requireContext(), aryProduc_tot)

        recycler_Tot.adapter = adaptador
        recycler_Tot.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

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
















