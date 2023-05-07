package com.example.restaurante

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restaurante.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null;
    private lateinit var auth: FirebaseAuth;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance();
        _binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo ####### INICIAR SESION CON UN USUARIO
        binding.btnLogin.setOnClickListener {
            if (!binding.editNomLog.text.isEmpty() && !binding.editPassLog.text.isEmpty()){
                auth.signInWithEmailAndPassword(binding.editNomLog.text.toString(),binding.editPassLog.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.v("usuario_log","usuario logueado")
                        //    Snackbar.make(binding.root,"usuario logueado",Snackbar.LENGTH_SHORT)
                            val bundle =Bundle()
                            bundle.putString("nombre",binding.editNomLog.text.toString())
                            bundle.putString("uid",auth.currentUser!!.uid)
                            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
                        }else{
                            Snackbar.make(binding.root,"el usuario no existe",Snackbar.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Snackbar.make(binding.root,"no ha ingresado datos",Snackbar.LENGTH_SHORT).show()
            }



        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment);

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


