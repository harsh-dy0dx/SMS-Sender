package com.example.smssender

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smssender.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding
var userMessage : String =""
var userNumber : String =""
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.send.setOnClickListener {
            userMessage = binding.message.text.toString()
            userNumber = binding.number.text.toString()
            sendSMS(userMessage, userNumber)
        }

    }

    fun sendSMS(userMessage: String, userNumber: String) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED)
        {
             ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),1)
        }
        else{
            val smsManager : SmsManager
            if (Build.VERSION.SDK_INT>=23){
                smsManager= this.getSystemService(SmsManager::class.java)
            } else{
           smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(userNumber,null,userMessage,null,null)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==1 && grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val smsManager : SmsManager
            if (Build.VERSION.SDK_INT>=23){
                smsManager= this.getSystemService(SmsManager::class.java)
            } else{
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(userNumber,null,userMessage,null,null)
            Toast.makeText(applicationContext, "Message sent", Toast.LENGTH_LONG).show()
        }
        }
    }
