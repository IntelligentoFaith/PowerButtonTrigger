package com.example.powerbuttonproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.widget.Toast

class SmsService : Service() {

     override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_BOOT_COMPLETED)

        val mReceiver = TheReceiver()
        registerReceiver(mReceiver,filter)
        return Service.START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        //create an intent that you want to start again.
        val intent = Intent(getApplicationContext(), SmsService::class.java)
        val pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000, pendingIntent)

        //reRegister the broadcast receiver
        val filter2 = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter2.addAction(Intent.ACTION_SCREEN_OFF)
        val mReceiver = TheReceiver()
        registerReceiver(mReceiver,filter2)

        Toast.makeText(applicationContext,"Sms Service REStarted!", Toast.LENGTH_LONG).show()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        sendBroadcast(Intent("YouWillNeverKillMe"))
    }
    inner class MyBinder: Binder(){
        internal val service:SmsService
        get(){
            return this@SmsService
        }
    }
}
