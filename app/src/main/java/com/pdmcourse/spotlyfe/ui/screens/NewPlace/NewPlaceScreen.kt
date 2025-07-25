package com.pdmcourse.spotlyfe.ui.screens.NewPlace

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.pdmcourse.spotlyfe.ui.components.SelectLocationMap
import com.pdmcourse.spotlyfe.ui.layout.CustomTopBar

@Composable
fun NewPlaceScreen(
  viewModel : NewPlaceViewModel = viewModel(factory = NewPlaceViewModel.Factory),
  onBackPressed: (() -> Unit) = {}
) {
  var name by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var latitude by remember { mutableStateOf(0.0) }
  var longitude by remember { mutableStateOf(0.0) }

  val onLocationChanged :(LatLng) -> Unit = { latlng ->
    latitude = latlng.latitude
    longitude = latlng.longitude
  }

  val onSavePlace: () -> Unit = {
    Log.d("SelectLocationMap", "Saving place with name: $name, description: $description, latitude: $latitude, longitude: $longitude")
    viewModel.savePlace(name, description, latitude, longitude)
    onBackPressed()
  }

  Scaffold(
    topBar = {
      CustomTopBar(
        title = "New Place",
        onBackPressed = onBackPressed
      )},
  ) { innerPadding ->
    Column( modifier = Modifier.padding(innerPadding)) {
      Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Name") },
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Description") },
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        )
        SelectLocationMap(onLocationChanged = onLocationChanged)
        Button(
          onClick = { onSavePlace() },
          modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
        ) {
          Text("Save")
        }
      }

    }
  }
}