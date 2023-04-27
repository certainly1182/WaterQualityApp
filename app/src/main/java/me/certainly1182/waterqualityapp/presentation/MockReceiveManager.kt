package me.certainly1182.waterqualityapp.presentation

import me.certainly1182.waterqualityapp.data.ConnectionState
import me.certainly1182.waterqualityapp.data.ReceiveManager
import me.certainly1182.waterqualityapp.data.SensorResult
import me.certainly1182.waterqualityapp.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.random.Random

class ReceiveManagerMock : ReceiveManager {
    private val dataFlow = MutableSharedFlow<Resource<SensorResult>>()

    override val data: MutableSharedFlow<Resource<SensorResult>>
        get() = dataFlow

    override fun startReceiving() {
        // Emit random values every second
        while (true) {
            val temperature = Random.nextFloat() * 100
            val pH = Random.nextFloat() * 14
            val conductivity = Random.nextFloat() * 100
            val turbidity = Random.nextFloat() * 100
            val connectionState = ConnectionState.Connected

            val result = SensorResult(
                temperature = temperature,
                pH = pH,
                conductivity = conductivity,
                turbidity = turbidity,
                connectionState = connectionState
            )

            dataFlow.tryEmit(Resource.Success(result))

            Thread.sleep(1000)
        }
    }

    override fun reconnect() {
        // Do nothing
    }

    override fun disconnect() {
        // Do nothing
    }

    override fun closeConnection() {
        // Do nothing
    }
}