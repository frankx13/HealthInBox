package com.studio.neopanda.healthinbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dropbox.core.android.Auth
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_login_drop_box.*


class LoginDropBoxActivity : BaseToolbarActivity() {

    private val targetBackActivity = MenuActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sign_in_button.setOnClickListener {
            Auth.startOAuth2Authentication(applicationContext, getString(R.string.APP_KEY))
        }

        configureToolbar(toolbar_log_db, targetBackActivity)
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_login_drop_box
    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }

    private fun getAccessToken() {
        val accessToken = Auth.getOAuth2Token() //generate Access Token
        if (accessToken != null) {
            //Store accessToken in SharedPreferences
            val prefs =
                getSharedPreferences("com.studio.neopanda.healthinbox", Context.MODE_PRIVATE)
            prefs.edit().putString("access-token", accessToken).apply()

            //Proceed to DropBoxActivity
            val intent = Intent(applicationContext, DropBoxActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
