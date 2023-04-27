package me.certainly1182.waterqualityapp.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.certainly1182.waterqualityapp.data.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class CalibrationViewModel @Inject constructor(
    val dataStoreManager: DataStoreManager,
    val coroutineScope: CoroutineScope
) : ViewModel() {

    var temperatureOffset = 0f

    var kValueTemp = 0f

    var pH4Voltage = 0f
    var pH7Voltage = 0f
    var pH10Voltage = 0f

    var turbidity1Voltage = 0f
    var turbidity2Voltage = 0f
    var turbidity1 = 0f
    var turbidity2 = 0f
    fun getPH(pHVoltage: Float): Float {
        val pH4 = 4.0f
        val pH7 = 7.0f
        val pH10 = 10.0f
        var pH47Gradient = 0f
        var pH107Gradient = 0f
        if (pH7Voltage == 0f) return 0f
        if (pH4Voltage != 0f) {
            pH47Gradient = (pH7 - pH4) / (pH7Voltage - pH4Voltage)
        }
        if (pH10Voltage != 0f) {
            pH107Gradient = (pH10 - pH7) / (pH10Voltage - pH7Voltage)
        }
        return if (pH47Gradient != 0f && pH107Gradient != 0f) {
            val pHGradient = (pH107Gradient + pH47Gradient) / 2
            val pHIntercept = pH7 - (pHGradient * pH7Voltage)
            (pHVoltage * pHGradient) + pHIntercept
        } else if (pH47Gradient != 0f) {
            val pHIntercept = pH7 - (pH47Gradient * pH7Voltage)
            (pHVoltage * pH47Gradient) + pHIntercept
        } else if (pH107Gradient != 0f) {
            val pHIntercept = pH7 - (pH107Gradient * pH7Voltage)
            (pHVoltage * pH107Gradient) + pHIntercept
        } else {
            0f
        }
    }

    fun getTurbidity(turbidityVoltage: Float): Float {
        val gradient = (turbidity2 - turbidity1) / (turbidity2Voltage - turbidity1Voltage)
        val yIntercept = turbidity1 - (gradient * turbidity1Voltage)
        return (turbidityVoltage * gradient) + yIntercept
    }

    fun updateTemperatureOffset(offset: Float) = coroutineScope.launch(Dispatchers.IO){
        dataStoreManager.updateTemperatureOffset(offset)
    }
    fun getTemperatureOffset(): Flow<Float> {
        return dataStoreManager.getTemperatureOffset()
    }
}