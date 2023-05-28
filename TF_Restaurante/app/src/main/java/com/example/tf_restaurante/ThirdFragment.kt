package com.example.tf_restaurante

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.tf_restaurante.databinding.FragmentThirdBinding
import com.google.firebase.auth.FirebaseAuth



class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth;

    override fun onResume() {

        super.onResume()
        //   (activity as AppCompatActivity)!!.supportActionBar!!.hide()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.stock)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance();
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root

    }


}