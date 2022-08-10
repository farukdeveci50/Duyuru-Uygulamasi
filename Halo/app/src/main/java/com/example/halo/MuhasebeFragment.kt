package com.example.halo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.halo.databinding.FragmentMuhasebeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MuhasebeFragment : Fragment() {
   private lateinit var tasarim : FragmentMuhasebeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ITRecyclerAdapter
    private var duyurular = arrayListOf<Duyuru>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firestore = Firebase.firestore

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentMuhasebeBinding.inflate(inflater,container,false)
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ITRecyclerAdapter()
        tasarim.rvMuhasebe.adapter = adapter
        tasarim.rvMuhasebe.layoutManager = LinearLayoutManager(requireContext())

        firestore.collection("Duyurular").whereEqualTo("alici","Muhasebe").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error != null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{

                if(value != null){
                    if(value.isEmpty){
                        Toast.makeText(requireContext(),"Duyuru Yok", Toast.LENGTH_SHORT).show()
                    }else{

                        val documents = value.documents
                        duyurular.clear()
                        for (document in documents){
                            val duyuru = document.get("duyuru") as String
                            val konu = document.get("konu") as String
                            val user = document.get("user") as String
                            val alici = document.get("alici") as String
                            val duyurum = Duyuru(alici,konu,duyuru)

                            duyurular.add(duyurum)
                            adapter.duyurular =duyurular
                        }

                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }


}