package com.example.powerbuttonproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(getApplicationContext(),SmsService::class.java))
        Toast.makeText(applicationContext,"Sms Service Started!", Toast.LENGTH_LONG).show()

    }
}
