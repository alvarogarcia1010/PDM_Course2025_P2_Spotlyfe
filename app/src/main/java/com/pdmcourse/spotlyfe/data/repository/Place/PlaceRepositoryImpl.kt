package com.pdmcourse.spotlyfe.data.repository.Place

import com.pdmcourse.spotlyfe.data.database.dao.PlaceDao
import com.pdmcourse.spotlyfe.data.database.entities.toDomain
import com.pdmcourse.spotlyfe.data.model.Place
import com.pdmcourse.spotlyfe.data.model.toDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaceRepositoryImpl(
  private val placeDao: PlaceDao,
) : PlaceRepository {

  override fun getSavedPlaces(): Flow<List<Place>> {
    return placeDao.getSavedPlaces().map { list ->
      list.map { it.toDomain() }
    }
  }

  override suspend fun savePlace(place: Place) {
    placeDao.savePlace(place.toDatabase())
  }
}