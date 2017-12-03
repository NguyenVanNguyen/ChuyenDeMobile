package com.example.holu.hinhtetvs1.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.holu.hinhtetvs1.R
import com.example.holu.hinhtetvs1.ui.main.HomeActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*

class LoginActivity : AppCompatActivity() {
    var mCardLogin : CardView? = null
    var mTxtName : TextView? = null
    var mImgLogo : ImageView? = null
    var mLinearLogin : LinearLayout? = null
    private var callbackManager: CallbackManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mCardLogin = findViewById(R.id.cardLoginFb)
        mTxtName = findViewById(R.id.txtAppName)
        mImgLogo = findViewById(R.id.logo)
        mLinearLogin = findViewById(R.id.linearLoginFb)

        loadAnim()
        initFb()

        mLinearLogin!!.setOnClickListener {
            val token = AccessToken.getCurrentAccessToken()
            if (token == null) run {
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("public_profile", "email", "user_birthday"))
            }
            else{
                goToHome()
            }
        }
    }

    private fun initFb() {
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Toast.makeText(this@LoginActivity,"Successful", Toast.LENGTH_LONG).show()
                Log.e("fb",loginResult.accessToken.userId)
                goToHome()
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Login attempt canceled.", Toast.LENGTH_LONG).show()
            }

            override fun onError(e: FacebookException) {
                Toast.makeText(this@LoginActivity, "Login attempt failed.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun goToHome() {
        val home = Intent(this, HomeActivity::class.java)
        startActivity(home)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadAnim() {
        val down = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        val up = AnimationUtils.loadAnimation(this, R.anim.move_up)
        val fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        mCardLogin!!.startAnimation(up)
        mTxtName!!.startAnimation(fadein)
        mImgLogo!!.startAnimation(down)
    }
}
