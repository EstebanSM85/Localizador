package com.ejemplo.localizador

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            // Permisos concedidos
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Solicitar permisos
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            MaterialTheme {
                MapScreen(fusedLocationClient)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(fusedLocationClient: FusedLocationProviderClient) {
    val context = LocalContext.current

    // Estado para la posición de la cámara (ubicación por defecto: Madrid, España)
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.4168, -3.7038), 12f)
    }

    // Estado para los marcadores
    var markers by remember { mutableStateOf(listOf<MarkerData>()) }

    // Estado para el campo de búsqueda
    var searchText by remember { mutableStateOf("") }

    // Obtener ubicación actual
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 15f)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Localizador") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Buscar dirección") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (searchText.isNotBlank()) {
                            searchLocation(context, searchText) { latLng, address ->
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                                markers = listOf(MarkerData(latLng, address))
                            }
                        }
                    }
                ) {
                    Text("Buscar")
                }
            }

            // Mapa
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    // Al hacer clic en el mapa, agregar un marcador
                    getAddressFromLatLng(context, latLng) { address ->
                        markers = listOf(MarkerData(latLng, address))
                    }
                }
            ) {
                // Mostrar marcadores
                markers.forEach { markerData ->
                    Marker(
                        state = MarkerState(position = markerData.position),
                        title = "Ubicación",
                        snippet = markerData.address
                    )
                }
            }
        }
    }
}

data class MarkerData(
    val position: LatLng,
    val address: String
)

fun searchLocation(
    context: android.content.Context,
    locationName: String,
    onLocationFound: (LatLng, String) -> Unit
) {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocationName(locationName, 1)

        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val latLng = LatLng(address.latitude, address.longitude)
            val addressText = address.getAddressLine(0) ?: "Dirección no disponible"
            onLocationFound(latLng, addressText)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getAddressFromLatLng(
    context: android.content.Context,
    latLng: LatLng,
    onAddressFound: (String) -> Unit
) {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (!addresses.isNullOrEmpty()) {
            val addressText = addresses[0].getAddressLine(0) ?: "Dirección no disponible"
            onAddressFound(addressText)
        } else {
            onAddressFound("Dirección no disponible")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onAddressFound("Error al obtener dirección")
    }
}