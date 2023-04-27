package me.certainly1182.waterqualityapp.data

import me.certainly1182.waterqualityapp.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface ReceiveManager {

    val data: MutableSharedFlow<Resource<SensorResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}