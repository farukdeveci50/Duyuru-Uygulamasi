    package com.example.halo

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import com.example.halo.databinding.FragmentAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

    class AdminFragment : Fragment() {
    private lateinit var tasarim : FragmentAdminBinding
    private lateinit var firestore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    private var mesaj = "asda"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = Firebase.firestore
        auth = Firebase.auth
        createNotificationChannel()



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentAdminBinding.inflate(inflater,container,false)
        tasarim.toolbarAdmin.title = "Admin Fragment"
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alicilar = ArrayList<String>()
        alicilar.add("İnsan Kaynakları")
        alicilar.add("IT")
        alicilar.add("Muhasebe")
        alicilar.add("Herkes")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, R.id.text1,alicilar)
        tasarim.spinner.adapter = adapter
        tasarim.buttonYayinla.setOnClickListener {
            auth.currentUser?.let {
                val alici = alicilar.get(tasarim.spinner.selectedItemPosition)
                val user = it.email
                val konu = tasarim.editTextKonu.text.toString()
                val duyuru = tasarim.editTextDuyuru.text.toString()
                val date = FieldValue.serverTimestamp()
                mesaj = alicilar.get(tasarim.spinner.selectedItemPosition)

                if(alici == "Herkes"){

                    val dataMap = HashMap<String, Any>()
                    dataMap.put("alici","IT")
                    dataMap.put("user",user!!)
                    dataMap.put("konu",konu)
                    dataMap.put("duyuru",duyuru)
                    dataMap.put("date",date)


                    firestore.collection("Duyurular").add(dataMap).addOnSuccessListener {
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                        //Toast.makeText(requireContext(),"Duyuru başarı ile yayınlandı",Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                    }

                    val dataMap1 = HashMap<String, Any>()
                    dataMap1.put("alici","İnsan Kaynakları")
                    dataMap1.put("user",user!!)
                    dataMap1.put("konu",konu)
                    dataMap1.put("duyuru",duyuru)
                    dataMap1.put("date",date)


                    firestore.collection("Duyurular").add(dataMap1).addOnSuccessListener {
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                        //Toast.makeText(requireContext(),"Duyuru başarı ile yayınlandı",Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                    }

                    val dataMap2 = HashMap<String, Any>()
                    dataMap2.put("alici","Muhasebe")
                    dataMap2.put("user",user!!)
                    dataMap2.put("konu",konu)
                    dataMap2.put("duyuru",duyuru)
                    dataMap2.put("date",date)


                    firestore.collection("Duyurular").add(dataMap2).addOnSuccessListener {
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                        Toast.makeText(requireContext(),"Duyuru başarı ile yayınlandı",Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                    }
                }else{
                    val dataMap = HashMap<String, Any>()
                    dataMap.put("alici",alici)
                    dataMap.put("user",user!!)
                    dataMap.put("konu",konu)
                    dataMap.put("duyuru",duyuru)
                    dataMap.put("date",date)


                    firestore.collection("Duyurular").add(dataMap).addOnSuccessListener {
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                        Toast.makeText(requireContext(),"Duyuru başarı ile yayınlandı",Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                        tasarim.editTextDuyuru.setText("")
                        tasarim.editTextKonu.setText("")
                    }


                }
                }

            sendNotification()

        }

        tasarim.buttonOlustur.setOnClickListener {
            val action = AdminFragmentDirections.actionAdminFragmentToKullaniciOlusturFragment()
            findNavController().navigate(action)
        }
    }


        private fun createNotificationChannel(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val name = "Notification Title"
                val descriptionText = "Notification Description"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                    description = descriptionText
                }
                val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

            }
        }

        private fun sendNotification(){
            val builder = NotificationCompat.Builder(requireContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setContentTitle("Duyuru Yayınlandı!")
                .setContentText(mesaj)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(requireContext())){
                notify(notificationId, builder.build())
            }
        }

}