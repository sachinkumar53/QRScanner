package com.sachin.app.qrscanner.service

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import com.sachin.app.qrscanner.main.MainActivity

@TargetApi(Build.VERSION_CODES.N)
class QRScannerTileService : TileService() {

    override fun onClick() {
        super.onClick()
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            startActivity(it)
            sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
        }
    }
}