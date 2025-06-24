package com.pdmcourse.spotlyfe.ui.screens.SavedPlaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pdmcourse.spotlyfe.ui.layout.CustomFloatingButton
import com.pdmcourse.spotlyfe.ui.layout.CustomTopBar

@Composable
fun SavedPlacesScreen(
  onNewPlacePressed : () -> Unit = {},
  onNewExperiencePressed: () -> Unit = {},
  onLogoutPressed: () -> Unit = {},
  placesViewModel: SavedPlacesViewModel = viewModel(factory = SavedPlacesViewModel.Factory),
) {

  val savePlaces by placesViewModel.savedPlaces.collectAsStateWithLifecycle(emptyList())

  val initialPosition = if (savePlaces.isNotEmpty()) {
    LatLng(savePlaces.first().latitude, savePlaces.first().longitude)
  } else {
    LatLng(13.679024407659101, -89.23578718993471)
  }

  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(initialPosition, 16f)
  }

  var uiSettings by remember {
    mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
  }
  var properties by remember {
    mutableStateOf(MapProperties(mapType = MapType.HYBRID))
  }

  Scaffold(
    topBar = { CustomTopBar(onLogoutPressed = { onLogoutPressed() })},
    floatingActionButton = { CustomFloatingButton(onClick = onNewPlacePressed)}

  ) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
      GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
      ) {
        savePlaces.map { place ->
          Marker(
            state = MarkerState(position = LatLng(place.latitude, place.longitude)),
            title = place.name,
            snippet = place.remark
          )
        }
      }
      Button(
        onClick = { onNewExperiencePressed() },
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
      ) {
        Text(text = "El recomendador de experiencias")
      }
    }
  }
}