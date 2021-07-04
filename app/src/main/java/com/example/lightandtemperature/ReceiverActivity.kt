package com.example.lightandtemperature

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.client.mqttv3.*


class ReceiverActivity : AppCompatActivity() {

    val currentTemp = 0
    val currentLight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val temperatureMaxSeekBar: SeekBar = findViewById(R.id.temperatureMaxSeekBar)
        val tempMax = findViewById<TextView>(R.id.tempMax)
        val temperatureMinSeekBar: SeekBar = findViewById(R.id.temperatureMinSeekBar)
        val tempMin = findViewById<TextView>(R.id.tempMin)
        val iluMaxSeekBar: SeekBar = findViewById(R.id.iluMaxSeekBar)
        val iluMax = findViewById<TextView>(R.id.iluMax)
        val iluMinSeekBar: SeekBar = findViewById(R.id.iluMinSeekBar)
        val iluMin = findViewById<TextView>(R.id.iluMin)

        temperatureMaxSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
            ) {
                tempMax.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(
                        this@ReceiverActivity,
                        "Progress is: " + seek.progress + "%",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
        temperatureMinSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
            ) {
                tempMin.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(
                        this@ReceiverActivity,
                        "Progress is: " + seek.progress + "%",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
        iluMaxSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
            ) {
                iluMax.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(
                        this@ReceiverActivity,
                        "Progress is: " + seek.progress + "%",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
        iluMinSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
            ) {
                iluMin.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(
                        this@ReceiverActivity,
                        "Progress is: " + seek.progress + "%",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })

        val btnConnect = findViewById<Button>(R.id.btnConnect)
        btnConnect.setOnClickListener {
            // your code to perform when the user clicks on the button
            
            Toast.makeText(this@ReceiverActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

        if(currentLight>iluMax.text.toString().toInt() &&currentLight<iluMin.text.toString().toInt()){
            Toast.makeText(
                    this,
                    "Luz fuera de los límites",
                    Toast.LENGTH_LONG
            )
        }
        if(currentTemp>tempMax.text.toString().toInt() &&currentTemp<tempMin.text.toString().toInt()){
            Toast.makeText(
                    this,
                    "Temperatura fuera de los límites",
                    Toast.LENGTH_LONG
            )
        }
    }


}