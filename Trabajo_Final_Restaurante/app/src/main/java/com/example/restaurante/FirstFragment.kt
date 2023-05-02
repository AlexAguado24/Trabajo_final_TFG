package com.example.restaurante

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restaurante.databinding.FragmentFirstBinding
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
        return binding.btnLogin;
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
                            // findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
                        }else{
                            Log.v("usuario_log","el usuario no existe")
                        }
                    }
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


