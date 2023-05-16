package com.example.tf_restaurante

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.tf_restaurante.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance();
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            if (!binding.editNomLog.text.isEmpty() && !binding.editPassLog.text.isEmpty()){
                auth.signInWithEmailAndPassword(binding.editNomLog.text.toString(),binding.editPassLog.text.toString())
                    .addOnCompleteListener {
                        if (!it.isSuccessful){

                            Snackbar.make(binding.root,"el usuario no existe", Snackbar.LENGTH_SHORT).show()

                        }else{
                            val bundle =Bundle()
                            var email=it.result.user!!.email
                            email=binding.editNomLog.text.toString()
                            bundle.putString("nombre",email)
                            bundle.putString("uid",auth.currentUser!!.uid)
                            findNavController().navigate(R.id.action_FirstFragment_to_secondActivity,bundle)

                        }
                    }
            }else{
                Snackbar.make(binding.root,"no ha ingresado datos", Snackbar.LENGTH_SHORT).show()
            }



        }

        binding.btnRegister.setOnClickListener {

            //todo No paso ningun dato porque se los pido en el SecondFragment directamente
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

        }

    }

    override fun onResume() {

        super.onResume()
     //   (activity as AppCompatActivity)!!.supportActionBar!!.hide()
        (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.base)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}