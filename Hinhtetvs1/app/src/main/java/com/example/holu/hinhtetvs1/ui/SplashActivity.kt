package com.example.holu.hinhtetvs1.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.holu.hinhtetvs1.R
import com.example.holu.hinhtetvs1.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val login = Intent(this,LoginActivity::class.java)
            startActivity(login)
        },2000)

    }
}
