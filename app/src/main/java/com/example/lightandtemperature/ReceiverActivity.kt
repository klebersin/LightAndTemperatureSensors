package com.example.lightandtemperature

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lightandtemperature.GestionDatos.manager.MQTTConnectionParams
import com.example.lightandtemperature.GestionDatos.manager.MQTTmanager
import com.example.lightandtemperature.GestionDatos.uiadapter.UIUpdaterInterface


class ReceiverActivity : AppCompatActivity(), UIUpdaterInterface {

    val currentTemp = 0
    val currentLight = 0
    var mqttManagerTemp:MQTTmanager? = null
    var mqttManagerLum:MQTTmanager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        resetUIWithConnection(false)
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

    override fun resetUIWithConnection(status: Boolean) {
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        connectBtn.isEnabled      = !status

        if (status){
            updateStatusViewWith("Connected")
        }else{
            updateStatusViewWith("Disconnected")
        }
    }

    override fun updateStatusViewWith(status: String) {
        val statusLabl = findViewById<TextView>(R.id.statusLabl)
        statusLabl.text = status
    }

    override fun update(message: String, topic: String) {
        Log.e(topic, message)
    }

    fun connect(view: View){
        var host = "tcp://stickyraccoon289.cloud.shiftr.io"

        var connectionParams1 = MQTTConnectionParams("MQTTSample", host, "temperatura","stickyraccoon289","q2LUs0YznGESIa7k")
        mqttManagerTemp = MQTTmanager(connectionParams1,applicationContext,this)
        mqttManagerTemp?.connect()

        var connectionParams2 = MQTTConnectionParams("MQTTSample", host, "luminocidad","stickyraccoon289","q2LUs0YznGESIa7k")
        mqttManagerTemp = MQTTmanager(connectionParams2,applicationContext,this)
        mqttManagerTemp?.connect()

    }



}