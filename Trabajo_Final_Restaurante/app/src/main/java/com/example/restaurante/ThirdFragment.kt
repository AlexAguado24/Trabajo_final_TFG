package com.example.restaurante

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.restaurante.databinding.FragmentThirdBinding
    import com.google.android.material.snackbar.Snackbar
    import com.google.firebase.database.DataSnapshot
    import com.google.firebase.database.DatabaseError

    import com.google.firebase.database.FirebaseDatabase
    import com.google.firebase.database.ValueEventListener


class ThirdFragment : Fragment() {

        private var _binding: FragmentThirdBinding? = null
      //  private lateinit var listaProducto: ArrayList<Producto>
        private lateinit var adaptador :AdaptadorProductos

        lateinit var aryBebidas:ArrayList<Producto>
        lateinit var aryComida:ArrayList<Producto>
        lateinit var aryPostre:ArrayList<Producto>
        private lateinit var db: FirebaseDatabase

        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            aryBebidas= ArrayList()
           // aryComida= ArrayList()
           // aryPostre= ArrayList()

            adaptador = AdaptadorProductos(requireContext(),aryBebidas)

          //  binding.recyThird.adapter = adaptador


            db = FirebaseDatabase.getInstance("https://restaurante-ces-default-rtdb.firebaseio.com/")
            _binding = FragmentThirdBinding.inflate(inflater, container, false)

            return binding.root

        }



        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            //TODO ##################################################           QUEDÉ ACÁ ##################################################

            db.getReference("bebidas").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        val bebidas:Producto = i.getValue(Producto::class.java)!!
                        bebidas.bebidasArrL.find {
                            it.equals("ME FALTA SEGUIR")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

       //     binding.recyThird.adapter = adaptador
   //         binding.recyThird.layoutManager = LinearLayoutManager(requireContext(),
          //      LinearLayoutManager.VERTICAL,false)

            binding.btnPagar.setOnClickListener {
                findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
                Snackbar.make(it,"Muchas gracias,vuelva pronto",Snackbar.LENGTH_SHORT).show()

            }

        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }





