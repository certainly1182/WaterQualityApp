package me.certainly1182.waterqualityapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.pow

@Composable
fun ConductivityScreen(
    conductivityVoltage: Float,
    temperature: Float,
    onCalibrationSaved: (kValueTemp: Float) -> Unit,
    navController: NavController
)  {
    var enteredConductivity by remember { mutableStateOf("") }
    var kValueTemp by remember { mutableStateOf(1f) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = AnnotatedString("%.0f".format((133.42f*conductivityVoltage.pow(3)
                    - 255.86f*conductivityVoltage.pow(2) + 857.39f*conductivityVoltage)
                    / kValueTemp) + "µS/cm"),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = AnnotatedString("at" + " %.1f".format(temperature) + "°C"),
            style = MaterialTheme.typography.h4
        )
        Text(
            text = "Enter the conductivity of the calibration solution:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = enteredConductivity,
            onValueChange = { enteredConductivity = it },
            label = { Text("Calibration Solution Conductivity (µS/cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = AnnotatedString("Current K Value: " + "%.1f".format(kValueTemp) + "cm⁻¹"),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                if (enteredConductivity.isNotBlank()) {
                    val rawECsolution = enteredConductivity.toFloat() * (1f+0.02f*(temperature-25f))
                    kValueTemp = rawECsolution / (133.42f*conductivityVoltage.pow(3)
                            - 255.86f*conductivityVoltage.pow(2) + 857.39f*conductivityVoltage)

                    //onCalibrationSaved(kValueTemp)
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Save Calibration")
        }
        Button(
            onClick = {
                onCalibrationSaved(kValueTemp)
                navController.navigate("sensor_screen")
            }
        ) {
            Text("Back")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewCondScreen() {
    ConductivityScreen(
        conductivityVoltage = 2.3f,
        onCalibrationSaved = {},
        navController = rememberNavController(),
        temperature = 19.3f
    )
}