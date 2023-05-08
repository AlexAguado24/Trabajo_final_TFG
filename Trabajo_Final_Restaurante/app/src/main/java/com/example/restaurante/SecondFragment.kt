package com.example.restaurante

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restaurante.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //TRAIGO LAS VARIABLES A ESTE FRAGMENT PARA TENER LOS VALORES DE SU POSTERIOR CUENTA BANCARIA ,ETC
    //private lateinit var nombre:String?=null
    //private lateinit var uid:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        //creé la bd el dia 2/5
        // TODO #########              DOS DIAS ANTES HAY QUE CREAR LA OTRA BD,YA TENGO LAS 2 CUENTAS DE GMAIL CREADAS                     ##########################
        db = FirebaseDatabase.getInstance("https://restaurante-ces-default-rtdb.firebaseio.com/")
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAtras.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.btnRegistr.setOnClickListener {

            //todo    ## CREAR USUARIO
        if (!binding.editNomReg.text.isEmpty() && !binding.editPassReg.text.isEmpty()){
                    auth.createUserWithEmailAndPassword(binding.editNomReg.text.toString(),binding.editPassReg.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Snackbar.make(binding.root,"Registro usuario",Snackbar.LENGTH_SHORT)
                                val bundle =Bundle()
                                bundle.putString("nombre",binding.editNomReg.text.toString())
                                bundle.putString("uid",auth.currentUser!!.uid)
                                findNavController().navigate(R.id.action_SecondFragment_to_secondActivity,bundle)
                            }else{
                                Snackbar.make(binding.root,"el usuario no existe",Snackbar.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Snackbar.make(binding.root,"no ha ingresado datos",Snackbar.LENGTH_SHORT).show()
                }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

