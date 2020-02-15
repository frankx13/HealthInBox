package com.studio.neopanda.healthinbox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dropbox.core.v2.users.FullAccount
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    private val IMAGE_REQUEST_CODE = 101
    private var ACCESS_TOKEN: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initData()
        setSupportActionBar(toolbar)

        if (!tokenExists()) {
            //No token
            //Back to LoginActivity
            val intent = Intent(this@MainActivity, LoginDropBoxActivity::class.java)
            startActivity(intent)
        }

        ACCESS_TOKEN = retrieveAccessToken()
        getUserAccount()

        fab.setOnClickListener {
            upload()
        }
    }

    private fun initData() {
        //
    }

    private fun initUI() {
        toolbar?.setNavigationOnClickListener {
            Toast.makeText(applicationContext, "Navigation icon was clicked", Toast.LENGTH_SHORT)
                .show()
        }
    }

    protected fun getUserAccount() {
        if (ACCESS_TOKEN == null) return
        UserAccountTask(
            DropboxClient.getClient(ACCESS_TOKEN!!),
            object : UserAccountTask.TaskDelegate {
                override fun onAccountReceived(account: FullAccount) {
                    //Print account's info
                    Log.d("User", account.email)
                    Log.d("User", account.name.displayName)
                    Log.d("User", account.accountType.name)
                    updateUI(account)
                }

                override fun onError(error: Exception) {
                    Log.d("User", "Error receiving account details.")
                }
            }).execute()
    }

    private fun updateUI(account: FullAccount) {
        name_textView.text = account.name.displayName
        email_textView.text = account.email
        val picasso = Picasso.get()

        picasso.load(account.profilePhotoUrl)
            .resize(200, 200)
            .into(imageView)
    }

    private fun upload() {
        if (ACCESS_TOKEN == null) return
        //Select image to upload
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Upload to Dropbox"
            ), IMAGE_REQUEST_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) return
        // Check which request we're responding to
        if (requestCode == IMAGE_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                //Image URI received
                val file = File(UriToPath.getPath(application, data.data!!)!!)
                UploadTask(
                    DropboxClient.getClient(ACCESS_TOKEN!!),
                    file,
                    applicationContext
                ).execute()
            }
        }
    }

    private fun tokenExists(): Boolean {
        val prefs =
            getSharedPreferences("com.example.valdio.dropboxintegration", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access-token", null)
        return accessToken != null
    }

    private fun retrieveAccessToken(): String? {
        //check if ACCESS_TOKEN is stored on previous app launches
        val prefs =
            getSharedPreferences("com.example.valdio.dropboxintegration", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access-token", null)
        return if (accessToken == null) {
            Log.d("AccessToken Status", "No token found")
            null
        } else {
            //accessToken already exists
            Log.d("AccessToken Status", "Token exists")
            accessToken
        }
    }
}
