package com.example.halo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.halo.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var tasarim : FragmentLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentLoginBinding.inflate(inflater,container,false)
        tasarim.toolbarLogin.title = "Log In Fragment"
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasarim.buttonSignup.setOnClickListener {

            auth.createUserWithEmailAndPassword(tasarim.emailText.text.toString(),tasarim.passwordText.text.toString()).addOnSuccessListener {

                val action = LoginFragmentDirections.actionLoginFragmentToGenelGirisFragment()
                findNavController().navigate(action)

            }.addOnFailureListener {exception->
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }

        tasarim.buttonLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(tasarim.emailText.text.toString(),tasarim.passwordText.text.toString()).addOnSuccessListener {

                val action = LoginFragmentDirections.actionLoginFragmentToGenelGirisFragment()
                findNavController().navigate(action)

            }.addOnFailureListener {
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }
}