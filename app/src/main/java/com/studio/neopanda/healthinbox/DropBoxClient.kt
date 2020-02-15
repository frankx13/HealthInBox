package com.studio.neopanda.healthinbox

import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.DbxRequestConfig


object DropboxClient {

    fun getClient(ACCESS_TOKEN: String): DbxClientV2 {
        // Create Dropbox client
        val config = DbxRequestConfig("dropbox/sample-app", "en_US")
        return DbxClientV2(config, ACCESS_TOKEN)
    }
}