package com.studio.neopanda.healthinbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dropbox.core.android.Auth
import kotlinx.android.synthetic.main.activity_login_drop_box.*


class LoginDropBoxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_drop_box)

        sign_in_button.setOnClickListener {
            Auth.startOAuth2Authentication(applicationContext, getString(R.string.APP_KEY)) }
    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }

    fun getAccessToken() {
        val accessToken = Auth.getOAuth2Token() //generate Access Token
        if (accessToken != null) {
            //Store accessToken in SharedPreferences
            val prefs =
                getSharedPreferences("com.studio.neopanda.healthinbox", Context.MODE_PRIVATE)
            prefs.edit().putString("access-token", accessToken).apply()

            //Proceed to MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
