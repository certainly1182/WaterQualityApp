package me.certainly1182.waterqualityapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.certainly1182.waterqualityapp.data.ConnectionState
import me.certainly1182.waterqualityapp.data.ReceiveManager
import me.certainly1182.waterqualityapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val receiveManager: ReceiveManager
) : ViewModel(){

    var initializingMessage by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var temperature by mutableStateOf(0f)
        private set

    var pHVoltage by mutableStateOf(0f)
        private set

    var conductivityVoltage by mutableStateOf(0f)
        private set

    var turbidityVoltage by mutableStateOf(0f)
        private set

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    private fun subscribeToChanges(){
        viewModelScope.launch {
            receiveManager.data.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        connectionState = result.data.connectionState
                        temperature = result.data.temperature
                        pHVoltage = result.data.pH
                        conductivityVoltage = result.data.conductivity
                        turbidityVoltage = result.data.turbidity
                    }

                    is Resource.Loading -> {
                        initializingMessage = result.message
                        connectionState = ConnectionState.CurrentlyInitializing
                    }

                    is Resource.Error -> {
                        errorMessage = result.errorMessage
                        connectionState = ConnectionState.Uninitialized
                    }
                }
            }
        }
    }

    fun disconnect(){
        receiveManager.disconnect()
    }

    fun reconnect(){
        receiveManager.reconnect()
    }

    fun initializeConnection(){
        errorMessage = null
        subscribeToChanges()
        receiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        receiveManager.closeConnection()
    }

}