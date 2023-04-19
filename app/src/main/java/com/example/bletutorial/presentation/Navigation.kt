package com.example.bletutorial.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    onBluetoothStateChanged:()->Unit
) {
    val viewModel: SensorViewModel = hiltViewModel()
    val calibrationViewModel: CalibrationViewModel = hiltViewModel()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.StartScreen.route){
        composable(Screen.StartScreen.route){
            StartScreen(navController = navController)
        }

        composable(Screen.SensorScreen.route){
            SensorScreen(
                viewModel = viewModel,
                calibrationViewModel = calibrationViewModel,
                onBluetoothStateChanged = onBluetoothStateChanged,
                navController = navController
            )
        }

        composable(Screen.TemperatureScreen.route){
            TemperatureScreen(
                temperature = viewModel.temperature,
                calibrationViewModel = calibrationViewModel,
                navController = navController,
                onCalibrationSaved = { temperatureOffset ->
                    calibrationViewModel.temperatureOffset = temperatureOffset
                }
            )
        }
    }

}

sealed class Screen(val route:String){
    object StartScreen:Screen("start_screen")
    object SensorScreen:Screen("sensor_screen")
    object TemperatureScreen:Screen("temperature_screen")
}