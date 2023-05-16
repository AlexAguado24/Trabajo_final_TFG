package com.example.tf_restaurante.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tf_restaurante.R
import com.example.tf_restaurante.model.ProductoTotal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class AdaptadorTotal(var context: Context, var lista: ArrayList<ProductoTotal>): RecyclerView.Adapter<AdaptadorTotal.MiHolder>() {
    private lateinit var db: FirebaseDatabase
    private lateinit var adaptador: AdaptadorTotal
    lateinit var aryProductos: ArrayList<ProductoTotal>
    lateinit var vista: View

    inner class MiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgTot: ImageView;
        var cantidad: TextView;
        var titulo: TextView;
        var preUn: TextView;
        var valTot: TextView;


        init {
            imgTot=itemView.findViewById(R.id.img_total)
            cantidad=itemView.findViewById(R.id.cant_item)
            titulo=itemView.findViewById(R.id.titulo_item)
            preUn=itemView.findViewById(R.id.prec_uni_item)
            valTot=itemView.findViewById(R.id.valor_tot_item)

            db = FirebaseDatabase.getInstance("https://restaurante-ces-default-rtdb.firebaseio.com/")
            instancias()
        }

    }

    private fun instancias() {
        aryProductos = ArrayList()
        adaptador = AdaptadorTotal(context,aryProductos)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiHolder {
        vista = LayoutInflater.from(context).inflate(R.layout.item_recycler_total,parent,false)

        return MiHolder(vista)
    }

    override fun onBindViewHolder(holder: MiHolder, position: Int) {
        var producto_T:ProductoTotal=lista.get(position)

        Glide.with(context).
        load(producto_T.imagen)
            .into(holder.imgTot)

        holder.cantidad.setText(producto_T.cantProducto.toString())



        holder.titulo.setText(producto_T.titulo.toString())
        holder.preUn.setText(producto_T.precio.toString())


        holder.valTot.setText(producto_T.valorTotal.toString())


    }



    override fun getItemCount(): Int {
        return lista.size;
    }
}