package com.example.lightandtemperature

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lightandtemperature.GestionDatos.manager.MQTTConnectionParams
import com.example.lightandtemperature.GestionDatos.manager.MQTTmanager
import com.example.lightandtemperature.GestionDatos.uiadapter.UIUpdaterInterface


class MainActivity : AppCompatActivity(), SensorEventListener, UIUpdaterInterface {

    private var sm: SensorManager? = null
    private var light: Sensor? = null
    private var temperature: Sensor? = null
    private lateinit var ilu: TextView
    lateinit var temp: TextView
    lateinit var changeAct: Button

    var mqttManagerTemp: MQTTmanager? = null
    var mqttManagerLum:MQTTmanager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ilu = findViewById<TextView>(R.id.ilu)
        temp = findViewById<TextView>(R.id.temp)
        changeAct = findViewById<Button>(R.id.buttonPass)

        changeAct.setOnClickListener{val intent = Intent(this,ReceiverActivity::class.java)
            startActivity(intent)}


        sm = (applicationContext.getSystemService(Service.SENSOR_SERVICE)) as SensorManager
        light = sm!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        temperature = sm!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sm!!.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME);
        sm!!.registerListener(this, temperature, SensorManager.SENSOR_DELAY_GAME);


    }
    fun startSend(view: View){
        var host = "tcp://stickyraccoon289.cloud.shiftr.io"

        var connectionParams1 = MQTTConnectionParams("MQTTSend", host, "temperatura","stickyraccoon289","q2LUs0YznGESIa7k")
        mqttManagerTemp = MQTTmanager(connectionParams1,applicationContext,this)
        mqttManagerTemp?.connect()

        var connectionParams2 = MQTTConnectionParams("MQTTSend2", host, "luminocidad","stickyraccoon289","q2LUs0YznGESIa7k")
        mqttManagerLum = MQTTmanager(connectionParams2,applicationContext,this)
        mqttManagerLum?.connect()
    }
    override fun onSensorChanged(event: SensorEvent) {
        val eachSensor = event.sensor

        when (eachSensor.type) {
            Sensor.TYPE_LIGHT -> {
                ilu.text = event.values[0].toString()
                mqttManagerLum?.publish(event.values[0].toInt().toString())
            }
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                temp.text = event.values[0].toString()
                mqttManagerTemp?.publish(event.values[0].toInt().toString())
            }
            else -> {
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.e("","")
    }

    override fun resetUIWithConnection(status: Boolean) {
        Log.e("","")
    }

    override fun updateStatusViewWith(status: String) {
        Log.e("","")
    }

    override fun update(message: String, topic: String) {
        Log.e("","")
    }

}