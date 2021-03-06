package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.ui.HomeActivity

class SplashActivity : AppCompatActivity() {
    private val Splash_Timeout:Long=3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({


            startActivity(Intent(this,
                HomeActivity::class.java))


            finish()
        }, Splash_Timeout)
    }
}