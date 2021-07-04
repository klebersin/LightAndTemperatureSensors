package com.example.lightandtemperature.GestionDatos.manager

import android.content.Context
import android.util.Log
import com.example.lightandtemperature.GestionDatos.uiadapter.UIUpdaterInterface
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.util.*

class MQTTmanager (val connectionParams: MQTTConnectionParams, val context: Context, val uiUpdater: UIUpdaterInterface?) {

    private var client = MqttAndroidClient(context, connectionParams.host,connectionParams.clientId + id(context),
        MemoryPersistence())
    private var uniqueID:String? = null
    private val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

    init {

        client.setCallback(object: MqttCallbackExtended {
            override fun connectComplete(b:Boolean, s:String) {
                Log.w("mqtt", s)
                uiUpdater?.resetUIWithConnection(true)
            }
            override fun connectionLost(throwable:Throwable) {
                uiUpdater?.resetUIWithConnection(false)
            }
            override fun messageArrived(topic:String, mqttMessage: MqttMessage) {
                Log.w("Mqtt", mqttMessage.toString())
                uiUpdater?.update(mqttMessage.toString(), topic)
            }
            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
            }
        })
    }
    fun connect(){
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.setAutomaticReconnect(true)
        mqttConnectOptions.setCleanSession(false)
        mqttConnectOptions.setUserName(this.connectionParams.username)
        mqttConnectOptions.setPassword(this.connectionParams.password.toCharArray())
        try
        {
            var params = this.connectionParams
            client.connect(mqttConnectOptions, null, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken:IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.setBufferEnabled(true)
                    disconnectedBufferOptions.setBufferSize(100)
                    disconnectedBufferOptions.setPersistBuffer(false)
                    disconnectedBufferOptions.setDeleteOldestMessages(false)
                    client.setBufferOpts(disconnectedBufferOptions)
                    subscribe(params.topic)

                }
                override fun onFailure(asyncActionToken:IMqttToken, exception:Throwable) {
                    Log.w("Mqtt", "Failed to connect to: " + params.host + exception.toString())
                }
            })
        }
        catch (ex:MqttException) {
            ex.printStackTrace()
        }
    }

    fun disconnect(){
        try {
            client.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    uiUpdater?.resetUIWithConnection(false)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    uiUpdater?.resetUIWithConnection(false)
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    // Subscribe to topic
    fun subscribe(topic: String){
        try
        {
            client.subscribe(topic, 0, null, object:IMqttActionListener {
                override fun onSuccess(asyncActionToken:IMqttToken) {
                    Log.w("Mqtt", "Subscription!")
                    uiUpdater?.updateStatusViewWith("Subscribed to Topic")
                }
                override fun onFailure(asyncActionToken:IMqttToken, exception:Throwable) {
                    Log.w("Mqtt", "Subscription fail!")
                    uiUpdater?.updateStatusViewWith("Falied to Subscribe to Topic")
                }
            })
        }
        catch (ex:MqttException) {
            System.err.println("Exception subscribing")
            ex.printStackTrace()
        }
    }

    // Unsubscribe the topic
    fun unsubscribe(topic: String){

        try
        {
            client.unsubscribe(topic,null,object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    uiUpdater?.updateStatusViewWith("UnSubscribed to Topic")
                    uiUpdater?.resetUIWithConnection(false)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    uiUpdater?.updateStatusViewWith("Failed to UnSubscribe to Topic")
                }

            })
        }
        catch (ex:MqttException) {
            System.err.println("Exception unsubscribe")
            ex.printStackTrace()
        }
    }

    fun publish(message:String){
        try
        {
            var msg = message
            client.publish(this.connectionParams.topic,msg.toByteArray(),0,false,null,object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.w("Mqtt", "Publish Success!")
                    uiUpdater?.updateStatusViewWith("Published to Topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.w("Mqtt", "Publish Failed!")
                    uiUpdater?.updateStatusViewWith("Failed to Publish to Topic")
                }

            })
        }
        catch (ex:MqttException) {
            System.err.println("Exception publishing")
            ex.printStackTrace()
        }
    }

    @Synchronized fun id(context:Context):String {
        if (uniqueID == null)
        {
            val sharedPrefs = context.getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE)
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)
            if (uniqueID == null)
            {
                uniqueID = UUID.randomUUID().toString()
                val editor = sharedPrefs.edit()
                editor.putString(PREF_UNIQUE_ID, uniqueID)
                editor.commit()
            }
        }
        return uniqueID!!
    }

}

data class MQTTConnectionParams(val clientId:String, val host: String, val topic: String, val username: String, val password: String){

}