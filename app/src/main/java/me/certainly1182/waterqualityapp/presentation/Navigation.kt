package me.certainly1182.waterqualityapp.presentation

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

        composable(Screen.PHScreen.route){
            PHScreen(
                pHVoltage = viewModel.pHVoltage,
                navController = navController,
                calibrationViewModel = calibrationViewModel,
                onCalibrationSaved = { pH4Voltage, pH7Voltage, pH10Voltage ->
                    calibrationViewModel.pH4Voltage = pH4Voltage
                    calibrationViewModel.pH7Voltage = pH7Voltage
                    calibrationViewModel.pH10Voltage = pH10Voltage}
            )
        }

        composable(Screen.ConductivityScreen.route){
            ConductivityScreen(
                conductivityVoltage = viewModel.conductivityVoltage,
                temperature = viewModel.temperature,
                navController = navController,
                onCalibrationSaved = { kValueTemp ->
                    calibrationViewModel.kValueTemp = kValueTemp
                }
            )
        }

        composable(Screen.TurbidityScreen.route){
            TurbidityScreen(
                turbidityVoltage = viewModel.turbidityVoltage,
                navController = navController,
                calibrationViewModel = calibrationViewModel,
                onCalibrationSaved = { turbidity1Voltage, turbidity2Voltage, turbidity1, turbidity2 ->
                    calibrationViewModel.turbidity1Voltage = turbidity1Voltage
                    calibrationViewModel.turbidity2Voltage = turbidity2Voltage
                    calibrationViewModel.turbidity1 = turbidity1
                    calibrationViewModel.turbidity2 = turbidity2
                }
            )
        }
    }

}

sealed class Screen(val route:String){
    object StartScreen:Screen("start_screen")
    object SensorScreen:Screen("sensor_screen")
    object TemperatureScreen:Screen("temperature_screen")
    object PHScreen:Screen("ph_screen")
    object ConductivityScreen:Screen("conductivity_screen")
    object TurbidityScreen:Screen("turbidity_screen")
}