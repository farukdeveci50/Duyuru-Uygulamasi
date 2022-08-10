package com.example.halo

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.halo.databinding.FragmentKullaniciOlusturBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class KullaniciOlusturFragment : Fragment() {
   private lateinit var tasarim : FragmentKullaniciOlusturBinding
   private lateinit var firestore : FirebaseFirestore
   private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = Firebase.firestore
        auth = Firebase.auth


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentKullaniciOlusturBinding.inflate(inflater,container,false)
        tasarim.toolbarKullaniciOlustur.title = "Kullanıcı Oluştur Fragment"
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alicilar = ArrayList<String>()
        alicilar.add("İnsan Kaynakları")
        alicilar.add("IT")
        alicilar.add("Muhasebe")
        alicilar.add("Admin")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, R.id.text1,alicilar)
        tasarim.spinner2.adapter = adapter

        tasarim.buttonOlustur.setOnClickListener {
            auth.currentUser?.let {
                val kullanici =alicilar.get(tasarim.spinner2.selectedItemPosition)
                val email =tasarim.editTextEmail.text.toString()
                val sifre = tasarim.editTextSifre.text.toString()

                val dataMap =HashMap<String, Any>()
                dataMap.put("kullanici",kullanici)
                dataMap.put("email",email)
                dataMap.put("sifre",sifre)

                firestore.collection("Kullanicilar").add(dataMap).addOnSuccessListener {
                    tasarim.editTextEmail.setText("")
                    tasarim.editTextSifre.setText("")
                    Snackbar.make(view,"Kullanıcı başarı ile oluşturuldu",Snackbar.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}