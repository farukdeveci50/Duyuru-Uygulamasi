package com.example.halo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.halo.databinding.FragmentDetayBinding

class DetayFragment : Fragment() {
    private lateinit var tasarim : FragmentDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentDetayBinding.inflate(inflater,container,false)

        val bundle:DetayFragmentArgs by navArgs()
        val gelenDuyuru = bundle.duyurum

        tasarim.textViewKonuDetay.text = gelenDuyuru.konu
        tasarim.textViewDuyuruDetay.text = gelenDuyuru.duyuru

        return tasarim.root
    }

}