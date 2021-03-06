package com.example.powerbuttonproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.telephony.SmsManager
import android.util.Log
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast


class TheReceiver : BroadcastReceiver() {
    companion object {
     //  var wasScreenOn = true
        private var lastTriggerTime:Long = 0
        private val ONE_MILLI = 1000
        protected val TWO_SEC = (2 * ONE_MILLI).toLong()
        protected val TRIGGER_THRESHOLD = 3
        protected var triggerInProgress = false
        protected var triggerCounter = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("#####","onReceive")
        checkAndSendSMS()

        if(intent.action.equals(Intent.ACTION_BOOT_COMPLETED)){
            val service = Intent(context, SmsService::class.java)
            service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startService(service)
            Toast.makeText(context,"Sms Service Restarted After Reboot!",Toast.LENGTH_LONG).show()
            checkAndSendSMS()
        }
      }



    private fun checkAndSendSMS() {
        if (((System.currentTimeMillis() - lastTriggerTime) <= TWO_SEC || (triggerCounter == 0)))
        {
            triggerCounter++
            lastTriggerTime = System.currentTimeMillis()
            Log.e("#######", "triggerCounter = " + triggerCounter)
        }
        else
        {
            triggerCounter = 0
        }
        if (triggerCounter > TRIGGER_THRESHOLD)
        {
            triggerInProgress = true
            Log.e("#######","triggerInProgress = " + triggerInProgress)
            Log.e("#######","triggerCounter = " + triggerCounter)

            val smsManager = SmsManager.getDefault() as SmsManager
            smsManager.sendTextMessage("0721425662", null, "I have pressed my power button at least 4 times, I could be in trouble", null, null)
            //0721425662
            triggerInProgress = false
            triggerCounter = 0

            Log.e("#######","triggerInProgress = " + triggerInProgress)

        }
    }

}
