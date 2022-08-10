package com.example.halo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.halo.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var tasarim : ActivitySplashScreenBinding
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(tasarim.root)

        tasarim.lottieAnimasyon.playAnimation()
        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },6000)
    }
}