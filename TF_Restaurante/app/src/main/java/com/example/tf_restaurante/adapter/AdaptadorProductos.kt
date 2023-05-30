package com.example.tf_restaurante.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tf_restaurante.R
import com.example.tf_restaurante.dialogs.DialogoProducto
import com.example.tf_restaurante.model.Producto



class AdaptadorProductos(var contexto: Context, var listado: ArrayList<Producto>,var soporteF:FragmentManager): RecyclerView.Adapter<AdaptadorProductos.MyHolder>() {



    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imagenProducto: ImageView
        var textoProducto: TextView
        var textoPrecio: TextView
        var linear_item:ConstraintLayout

        init {

            imagenProducto = itemView.findViewById(R.id.imagen_item)
            textoProducto = itemView.findViewById(R.id.nombre_item)
            textoPrecio = itemView.findViewById(R.id.precio_item)
            linear_item=itemView.findViewById(R.id.linear_item)


        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(contexto).inflate(R.layout.item_producto, parent, false)

        return MyHolder(view)

    }

    @SuppressLint("SuspiciousIndentation", "ResourceType")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var producto:Producto=listado.get(position)
       Glide.with(contexto).load(producto.imagen).into(holder.imagenProducto)
       // Glide.with(contexto).load(producto.imagen).apply(RequestOptions.circleCropTransform()).into(holder.imagenProducto)


        holder.textoProducto.setText(producto.titulo)
        holder.textoPrecio.setText(producto.precio.toString())





            holder.itemView.setOnClickListener {

            val dialogo = DialogoProducto.newInstance(listado[position])

                dialogo.show(soporteF,"")



        }




    }

    override fun getItemCount(): Int {
        return listado.size
    }
    fun verComida(producto: Producto){
        listado.add(producto)
        notifyItemInserted(listado.size-1)
    }



}