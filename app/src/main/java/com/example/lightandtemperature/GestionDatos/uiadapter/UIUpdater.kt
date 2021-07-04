package com.example.lightandtemperature.GestionDatos.uiadapter

interface UIUpdaterInterface {

    fun resetUIWithConnection(status: Boolean)
    fun updateStatusViewWith(status: String)
    fun update(message: String, topic: String)

}