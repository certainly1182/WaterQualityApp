package me.certainly1182.waterqualityapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PHScreen(
    pHVoltage: Float,
    onCalibrationSaved: (pH4Voltage: Float, pH7Voltage: Float, pH10Voltage: Float) -> Unit,
    navController: NavController,
    calibrationViewModel: CalibrationViewModel
) {
    var pH4Voltage by remember { mutableStateOf(0f) }
    var pH7Voltage by remember { mutableStateOf(0f) }
    var pH10Voltage by remember { mutableStateOf(0f) }

    Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = AnnotatedString("%.1f".format(calibrationViewModel.getPH(pHVoltage))),
            style = MaterialTheme.typography.h1
        )
        Text(
            text = AnnotatedString("%.2f".format(pHVoltage) + "V"),
            style = MaterialTheme.typography.h1
        )
        Text(
            text = "Use two point calibration to calibrate the pH sensor",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    pH4Voltage = pHVoltage
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.7f)
            ) {
                Text(
                    text = "Set pH 4.0\nCalibration",
                )
            }
            Text(
                text = "${pH4Voltage}V ",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            )
            Button(
                onClick = {
                    pH4Voltage = 0.0f
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.5f)
            ) {
                Text(
                    text = "Reset",
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    pH7Voltage = pHVoltage
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.7f)
            ) {
                Text(
                    text = "Set pH 7.0\nCalibration"
                )
            }
            Text(
                text = "${pH4Voltage}V ",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            )
            Button(
                onClick = {
                    pH7Voltage = 0.0f
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.5f)
            ) {
                Text(
                    text = "Reset",
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    pH10Voltage = pHVoltage
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.7f)
            ) {
                Text(
                    text = "Set pH 10.0\nCalibration",
                )
            }
            Text(
                text = "${pH4Voltage}V ",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            )
            Button(
                onClick = {
                    pH10Voltage = 0.0f
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(0.5f)
            ) {
                Text(
                    text = "Reset",
                )
            }
        }
        Button(
            onClick = {
                //calibrationViewModel.saveData("pH4Voltage", pH4Voltage)
                onCalibrationSaved(pH4Voltage, pH7Voltage, pH10Voltage)
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
private fun PreviewPHScreen() {
    PHScreen(
        pHVoltage = 1.54f,
        onCalibrationSaved = { _, _, _ -> },
        navController = rememberNavController(),
        calibrationViewModel = CalibrationViewModel()
    )
}*/