package com.pdmcourse.spotlyfe.data.repository.Place

import com.pdmcourse.spotlyfe.data.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
  fun getSavedPlaces(): Flow<List<Place>>
  suspend fun savePlace(place: Place)
}