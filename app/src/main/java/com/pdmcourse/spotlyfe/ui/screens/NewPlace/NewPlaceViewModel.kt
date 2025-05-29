package com.pdmcourse.spotlyfe.ui.screens.NewPlace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse.spotlyfe.SpotLyfeApplication
import com.pdmcourse.spotlyfe.data.model.Place
import com.pdmcourse.spotlyfe.data.repository.Place.PlaceRepository
import kotlinx.coroutines.launch

class NewPlaceViewModel(
  private val placeRepository : PlaceRepository
): ViewModel() {
  fun savePlace(name: String, remark: String, latitude: Double, longitude: Double) {
    viewModelScope.launch {
      placeRepository.savePlace(Place(
        name = name,
        remark = remark,
        latitude = latitude,
        longitude = longitude
      ))
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as SpotLyfeApplication)
        NewPlaceViewModel(application.appProvider.providePlaceRepository())
      }
    }
  }
}