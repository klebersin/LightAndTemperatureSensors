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


    lateinit var tempMin: TextView
    lateinit var tempMax: TextView
    lateinit var iluMax: TextView
    lateinit var iluMin: TextView
    lateinit var alertText: TextView
    lateinit var alertTemp: TextView
    var mqttManagerTemp:MQTTmanager? = null
    var mqttManagerLum:MQTTmanager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        resetUIWithConnection(false)
        val temperatureMaxSeekBar: SeekBar = findViewById(R.id.temperatureMaxSeekBar)
         tempMax = findViewById<TextView>(R.id.tempMax)
        val temperatureMinSeekBar: SeekBar = findViewById(R.id.temperatureMinSeekBar)
         tempMin = findViewById<TextView>(R.id.tempMin)
        val iluMaxSeekBar: SeekBar = findViewById(R.id.iluMaxSeekBar)
        iluMax = findViewById<TextView>(R.id.iluMax)
        val iluMinSeekBar: SeekBar = findViewById(R.id.iluMinSeekBar)
         iluMin = findViewById<TextView>(R.id.iluMin)
        alertText = findViewById<TextView>(R.id.alertText)
        alertTemp = findViewById<TextView>(R.id.alertTemp)

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
        if (topic == "temperatura"){
            if(message.toInt()>tempMax.text.toString().toInt() || message.toInt()<tempMin.text.toString().toInt()){
                alertTemp.text = "Regular temperatura"
            }else{
                alertTemp.text = ""
            }
        }
        if(topic == "luminocidad"){
            if(message.toInt()>iluMax.text.toString().toInt() || message.toInt()<iluMin.text.toString().toInt()){
                alertText.text = "Regular luminocidad"
            }else{
                alertText.text = ""
            }
        }



    }

    fun connect(view: View){
        var host = "tcp://stickyraccoon289.cloud.shiftr.io"

        var connectionParams1 = MQTTConnectionParams("MQTTSample", host, "temperatura","stickyraccoon289","q2LUs0YznGESIa7k")
        mqttManagerTemp = MQTTmanager(connectionParams1,applicationContext,this)
        mqttManagerTemp?.connect()

        var connectionParams2 = MQTTConnectionParams("MQTTSample2", host, "luminocidad","stickyraccoon289","q2LUs0YznGESIa7k")
        mqttManagerLum = MQTTmanager(connectionParams2,applicationContext,this)
        mqttManagerLum?.connect()

    }



}