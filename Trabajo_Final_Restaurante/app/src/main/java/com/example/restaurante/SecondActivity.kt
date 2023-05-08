package com.example.restaurante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurante.databinding.ActivityMainBinding
import com.example.restaurante.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SecondActivity : AppCompatActivity() {
    private  var nombre:String?=null
    private  var uid:String?=null
    private lateinit var binding: ActivitySecondBinding
    private lateinit var db: FirebaseDatabase
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseDatabase.getInstance("https://restaurante-ces-default-rtdb.firebaseio.com/")
     //   auth = FirebaseAuth.getInstance();




        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

       nombre= intent.extras!!.getString("nombre")
       uid= intent.extras!!.getString("uid")

        Snackbar.make(binding.root, nombre.toString(),Snackbar.LENGTH_SHORT).show()

        //con la llamada a la referencia y creamos el nodo usuario,primero me creo la referencia usuario en la BD, si no existe me la crea
        db.getReference("usuar ios").child(uid.toString())
            .child("nombre").setValue(nombre)          //busco el identificador usuario dentro de t0do el desplegable que hay debajo busco lo que me interese en este caso nombre  //lo que me guarda en el set value,es el nombre recuperado

    }
}