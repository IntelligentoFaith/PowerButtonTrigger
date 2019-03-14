package com.example.powerbuttonproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.telephony.SmsManager
import android.util.Log
import android.support.v4.content.ContextCompat.startActivity



class TheReceiver : BroadcastReceiver() {
    companion object {
       var wasScreenOn = true
        private var lastTriggerTime:Long = 0
        private val ONE_MILLI = 1000
        protected val ONE_SEC = (1 * ONE_MILLI).toLong()
        protected val TWO_SEC = (2 * ONE_MILLI).toLong()
        protected val THREE_SEC = (3 * ONE_MILLI).toLong()
        protected val TRIGGER_THRESHOLD = 3
        protected var triggerInProgress = false
        protected var triggerCounter = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("#####","onReceive")
        checkAndSendSMS(context.applicationContext)

        if (intent.action.equals(Intent.ACTION_SCREEN_ON)){
            wasScreenOn = true
            Log.e("#######","wasScreenOn" + wasScreenOn)
        }
        else if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            wasScreenOn = false
            Log.e("#######","wasScreenOn" + wasScreenOn)
        }
      }


    private fun checkAndSendSMS(context:Context) {
        if (((System.currentTimeMillis() - lastTriggerTime) <= TWO_SEC || (triggerCounter == 0)))
        {
            triggerCounter++
            lastTriggerTime = System.currentTimeMillis()
            Log.e("#######","triggerCounter = " + triggerCounter)

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

            triggerInProgress = false
            triggerCounter = 0

            Log.e("#######","triggerInProgress = " + triggerInProgress)

        }
    }

}
