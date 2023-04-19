package com.example.bletutorial.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.roundToInt

@Composable
fun TemperatureScreen(
    temperature: Float,
    onCalibrationSaved: (offset: Float) -> Unit,
    navController: NavController,
    calibrationViewModel: CalibrationViewModel
) {
    var enteredTemperature by remember { mutableStateOf("") }
    var offset by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = AnnotatedString("%.1f".format(temperature + offset) + "°C"),
            style = MaterialTheme.typography.h1
        )
        Text(
            text = "Enter the temperature measured using a separate device to calibrate:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = enteredTemperature,
            onValueChange = { enteredTemperature = it },
            label = { Text("Temperature (°C)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Current Offset: ${offset}°C",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                if (enteredTemperature.isNotBlank()) {
                    offset = enteredTemperature.toFloat() - temperature
                    offset = (offset * 100).roundToInt().toFloat() / 100
                    onCalibrationSaved(offset)
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Save Calibration")
        }
        Button(
            onClick = {
                navController.navigate("sensor_screen")
            }
        ) {
            Text("Back")
        }
    }
}

@Preview
@Composable
private fun PreviewTempScreen() {
    TemperatureScreen(
        temperature = 23.3f,
        onCalibrationSaved = {},
        navController = rememberNavController(),
        calibrationViewModel = CalibrationViewModel()
    )
}