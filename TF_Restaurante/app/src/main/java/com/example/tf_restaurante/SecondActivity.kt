package com.example.tf_restaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tf_restaurante.adapter.AdaptadorProductos
import com.example.tf_restaurante.databinding.ActivitySecondBinding
import com.example.tf_restaurante.dialogs.DialogoProducto
import com.example.tf_restaurante.model.Producto
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    private var nombre: String? = null
    private var uid: String? = null
    private lateinit var binding: ActivitySecondBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var adaptador: AdaptadorProductos
    lateinit var aryProductos: ArrayList<Producto>




    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        db = FirebaseDatabase.getInstance("https://restaurante-ces-default-rtdb.firebaseio.com/")
        //   auth = FirebaseAuth.getInstance();
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instancias()
        configurarRecycler()

        binding.recyThird

        binding.btnComida.setOnClickListener(this)
        binding.btnPostre.setOnClickListener(this)
        binding.btnPagar.setOnClickListener(this)
        binding.btnBebida.setOnClickListener(this)



        nombre = intent.extras!!.getString("nombre")
        uid = intent.extras!!.getString("uid")

        //   Snackbar.make(binding.root, nombre.toString(), Snackbar.LENGTH_SHORT).show()

        //con la llamada a la referencia y creamos el nodo usuario,primero me creo la referencia usuario en la BD, si no existe me la crea
        db.getReference("usuarios").child(uid.toString())
            .child("nombre")
            .setValue(nombre)          //busco el identificador usuario dentro de t0do el desplegable que hay debajo busco lo que me interese en este caso nombre  //lo que me guarda en el set value,es el nombre recuperado


    }




    private fun configurarRecycler() {

        binding.recyThird.adapter = adaptador
        binding.recyThird.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL, false
        )
    }

    private fun instancias() {

        aryProductos = ArrayList()

        //adaptador = AdaptadorProductos(applicationContext, aryProductos)
        adaptador = AdaptadorProductos(this,aryProductos, supportFragmentManager)


    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_pagar -> {
                var intent: Intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_bebida -> {
                adaptador.listado.clear()
                adaptador.notifyDataSetChanged()
                db.getReference("bebidas")
                    .child("productos")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) { // acá saca el estado del nodo
                                val producto = i.getValue(Producto::class.java)
                              //  Log.v("salida", producto!!.precio.toString())
                                adaptador.verComida(producto!!)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            //ERROR EN LA COMUNICACIÓN
                        }
                    })

            }

            R.id.btn_comida -> {
                adaptador.listado.clear()
                adaptador.notifyDataSetChanged()
                db.getReference("comidas")
                    .child("productos")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) { // acá saca el estado del nodo
                                val producto = i.getValue(Producto::class.java)
                               // Log.v("salida", producto!!.precio.toString())
                                adaptador.verComida(producto!!)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            //ERROR EN LA COMUNICACIÓN
                        }
                    })

            }
            R.id.btn_postre -> {
                adaptador.listado.clear()
                adaptador.notifyDataSetChanged()
                db.getReference("postres")
                    .child("productos")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) { // acá saca el estado del nodo
                                val producto = i.getValue(Producto::class.java)
                                //Log.v("salida", producto!!.precio.toString())
                                adaptador.verComida(producto!!)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            //ERROR EN LA COMUNICACIÓN
                        }
                    })

            }


        }
    }









}