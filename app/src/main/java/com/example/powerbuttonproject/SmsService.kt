package com.example.powerbuttonproject

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder

class SmsService : Service() {

     override fun onBind(intent: Intent): IBinder? {
        return null
    }

     override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        //filter.addAction(Intent.ACTION_USER_PRESENT)

        val mReceiver = TheReceiver()
        registerReceiver(mReceiver,filter)
        return super.onStartCommand(intent, flags, startId)
    }
    inner class MyBinder: Binder(){
        internal val service:SmsService
        get(){
            return this@SmsService
        }
    }
}
