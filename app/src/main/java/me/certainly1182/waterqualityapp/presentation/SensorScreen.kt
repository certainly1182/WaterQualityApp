package me.certainly1182.waterqualityapp.presentation

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.certainly1182.waterqualityapp.data.ConnectionState
import me.certainly1182.waterqualityapp.presentation.permissions.PermissionUtils
import me.certainly1182.waterqualityapp.presentation.permissions.SystemBroadcastReceiver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SensorScreen(
    onBluetoothStateChanged:()->Unit,
    viewModel: SensorViewModel,
    navController: NavController,
    calibrationViewModel: CalibrationViewModel
) {

    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
        val action = bluetoothState?.action ?: return@SystemBroadcastReceiver
        if(action == BluetoothAdapter.ACTION_STATE_CHANGED){
            onBluetoothStateChanged()
        }
    }

    val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)
    val lifecycleOwner = LocalLifecycleOwner.current
    val bleConnectionState = viewModel.connectionState

    val savedTempOffset = runBlocking {
        calibrationViewModel.getTemperatureOffset().first()
    }

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_START){
                    permissionState.launchMultiplePermissionRequest()
                    if(permissionState.allPermissionsGranted && bleConnectionState == ConnectionState.Disconnected){
                        viewModel.reconnect()
                    }
                }
                if(event == Lifecycle.Event.ON_STOP){
                    if (bleConnectionState == ConnectionState.Connected){
                        viewModel.disconnect()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    LaunchedEffect(key1 = permissionState.allPermissionsGranted){
        if(permissionState.allPermissionsGranted){
            if(bleConnectionState == ConnectionState.Uninitialized){
                viewModel.initializeConnection()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(
                    BorderStroke(
                        5.dp, Color.Blue
                    ),
                    RoundedCornerShape(10.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(bleConnectionState == ConnectionState.CurrentlyInitializing){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator()
                    if(viewModel.initializingMessage != null){
                        Text(
                            text = viewModel.initializingMessage!!
                        )
                    }
                }
            }else if(!permissionState.allPermissionsGranted){
                Text(
                    text = "Go to the app setting and allow the missing permissions.",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }else if(viewModel.errorMessage != null){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                   Text(
                       text = viewModel.errorMessage!!
                   )
                    Button(
                        onClick = {
                            if(permissionState.allPermissionsGranted){
                                viewModel.initializeConnection()
                            }
                        }
                    ) {
                        Text(
                            "Try again"
                        )
                    }
                }
            }else if(bleConnectionState == ConnectionState.Connected){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    ClickableText(
                        text = AnnotatedString(
                        "Temperature: " +
                            "%.1f".format(viewModel.temperature +
                            savedTempOffset) +
                            "°C"
                        ),
                        onClick = { navController.navigate("temperature_screen") },
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .border(
                                BorderStroke(
                                    5.dp, Color.Cyan
                                ),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    )
                    ClickableText(
                        text = AnnotatedString("pH: ${calibrationViewModel
                            .getPH(pHVoltage = viewModel.pHVoltage)}"),
                        onClick = { navController.navigate("ph_screen") },
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .border(
                                BorderStroke(
                                    5.dp, Color.Cyan
                                ),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    )
                    ClickableText(
                        text = AnnotatedString("Conductivity: ${viewModel.conductivityVoltage}"),
                        onClick = { navController.navigate("conductivity_screen") },
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .border(
                                BorderStroke(
                                    5.dp, Color.Cyan
                                ),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    )
                    ClickableText(
                        text = AnnotatedString("Turbidity: ${viewModel.turbidityVoltage}"),
                        onClick = { navController.navigate("turbidity_screen") },
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .border(
                                BorderStroke(
                                    5.dp, Color.Cyan
                                ),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    )
                }
            }else if(bleConnectionState == ConnectionState.Disconnected){
                Button(onClick = {
                    viewModel.initializeConnection()
                }) {
                    Text("Initialize again")
                }
            }
        }
    }

}

/*@Preview(
    showBackground = true
)
@Composable
fun SensorScreenPreview() {
    SensorScreen(
        onBluetoothStateChanged = {},
        navController = rememberNavController(),
        viewModel = SensorViewModel(ReceiveManagerMock()),
        calibrationViewModel = CalibrationViewModel(dataStore = _)
    )
}*/