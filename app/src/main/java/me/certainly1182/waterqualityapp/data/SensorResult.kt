package me.certainly1182.waterqualityapp.data

data class SensorResult(
    val temperature:Float,
    val pH:Float,
    val conductivity:Float,
    val turbidity:Float,
    val connectionState: ConnectionState
)
