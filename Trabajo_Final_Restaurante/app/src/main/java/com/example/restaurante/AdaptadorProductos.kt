package com.example.restaurante

    import android.content.Context
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.LinearLayout
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView

    import com.google.android.material.snackbar.Snackbar


    class AdaptadorProductos(var contexto: Context, var listado: List<Producto>) : RecyclerView.Adapter<AdaptadorProductos.MyHolder>() {

        inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var imagenProducto: ImageView
            var textoProducto: TextView
            var linear: LinearLayout

            init {
                imagenProducto = itemView.findViewById(R.id.imagen_item)
                textoProducto = itemView.findViewById(R.id.nombre_item)
                linear = itemView.findViewById(R.id.linear_item)
            }
        }

        fun aniadirProducto(producto: Producto){
            (listado as ArrayList<Producto>).add(producto)
            notifyItemInserted(listado.size -1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
            return MyHolder(view)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            val producto = listado[position]

            holder.linear.setOnClickListener {
                Snackbar.make(holder.itemView,"El precio es ${producto.valor}", Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int {
            return listado.size
        }
    }