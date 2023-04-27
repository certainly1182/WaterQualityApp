package me.certainly1182.waterqualityapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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

@Composable
fun TurbidityScreen(
    turbidityVoltage: Float,
    onCalibrationSaved: (turbidity1Voltage: Float, turbidity2Voltage: Float, turbidity1: Float, turbidity2: Float) -> Unit,
    navController: NavController,
    calibrationViewModel: CalibrationViewModel
) {
    var enteredTurbidity1 by remember { mutableStateOf("0") }
    var enteredTurbidity2 by remember { mutableStateOf("0") }
    var turbidity1Voltage by remember { mutableStateOf(0f) }
    var turbidity2Voltage by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = AnnotatedString("%.0f".format(calibrationViewModel
                .getTurbidity(turbidityVoltage)) + "NTU"),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = AnnotatedString("%.2f".format(turbidityVoltage) + "V"),
            style = MaterialTheme.typography.h1
        )
        Text(
            text = "Use two turbidity standards to calibrate:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Point 1:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = enteredTurbidity1,
                onValueChange = { enteredTurbidity1 = it },
                label = { Text("Turbidity (NTU)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f)
            )
            Text(
                text = "${turbidity1Voltage}V ",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            )
            Button(
                onClick = {
                     turbidity1Voltage = turbidityVoltage
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.6f)
            ) {
                Text(
                    text = "Set",
                )
            }
            Button(
                onClick = {
                    turbidity1Voltage = 0.0f
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.6f)
            ) {
                Text(
                    text = "Reset",
                )
            }
        }
        Text(
            text = "Point 2:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = enteredTurbidity2,
                onValueChange = { enteredTurbidity2 = it },
                label = { Text("Turbidity (NTU)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f)
            )
            Text(
                text = "${turbidity2Voltage}V ",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            )
            Button(
                onClick = {
                    turbidity2Voltage = turbidityVoltage
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.6f)
            ) {
                Text(
                    text = "Set",
                )
            }
            Button(
                onClick = {
                    turbidity2Voltage = 0.0f
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.6f)
            ) {
                Text(
                    text = "Reset",
                )
            }
        }
        Button(
            onClick = {

            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Save Calibration")
        }
        Button(
            onClick = {
                onCalibrationSaved(turbidity1Voltage, turbidity2Voltage, enteredTurbidity1.toFloat(), enteredTurbidity2.toFloat())
                navController.navigate("sensor_screen")
            }
        ) {
            Text("Back")
        }
    }
}

/*@Preview(
    showBackground = true
)
@Composable
private fun PreviewTempScreen() {
    TurbidityScreen(
        turbidityVoltage = 2.35f,
        onCalibrationSaved = { _, _, _, _ -> },
        navController = rememberNavController(),
        calibrationViewModel = CalibrationViewModel()
    )
}*/