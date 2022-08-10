package com.example.halo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.halo.databinding.FragmentGenelGirisBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenelGirisFragment : Fragment() {
    private lateinit var tasarim : FragmentGenelGirisBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firestore = Firebase.firestore



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentGenelGirisBinding.inflate(inflater,container,false)
        tasarim.toolbarGenelGiris.title = "Genel Giris Fragment"
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasarim.buttonGiris.setOnClickListener {
            val email = tasarim.etEmail.text.toString()
            val sifre = tasarim.etSifre.text.toString()

            firestore.collection("Kullanicilar").whereEqualTo("email","${email}").addSnapshotListener { value, error ->
                if (error != null){
                    Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_SHORT).show()
                }else{

                    if(value != null){
                        if(value.isEmpty){
                            Toast.makeText(requireContext(),"Kullanıcı Yok!",Toast.LENGTH_SHORT).show()
                        }else{
                            val documents = value.documents
                            for(document in documents){
                                val kullanici =document.get("kullanici") as String
                                val password = document.get("sifre") as String
                                if(sifre == password){
                                    tasarim.etEmail.setText("")
                                    tasarim.etSifre.setText("")
                                    if(kullanici == "IT"){
                                        val action = GenelGirisFragmentDirections.actionGenelGirisFragmentToITFragment()
                                        findNavController().navigate(action)
                                    }else if (kullanici == "İnsan Kaynakları"){
                                        val action = GenelGirisFragmentDirections.actionGenelGirisFragmentToInsanKaynaklariFragment()
                                        findNavController().navigate(action)
                                    }else if (kullanici == "Muhasebe"){
                                        val action = GenelGirisFragmentDirections.actionGenelGirisFragmentToMuhasebeFragment()
                                        findNavController().navigate(action)
                                    }else if (kullanici == "Admin"){
                                        val action = GenelGirisFragmentDirections.actionGenelGirisFragmentToAdminFragment()
                                        findNavController().navigate(action)
                                    }else{
                                        Toast.makeText(requireContext(),"Bilinmeyen Hata",Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(requireContext(),"Kullanıcı adı veya şifre yanlış",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}