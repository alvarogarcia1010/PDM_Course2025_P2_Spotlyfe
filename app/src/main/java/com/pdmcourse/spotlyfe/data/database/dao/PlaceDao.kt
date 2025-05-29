package com.pdmcourse.spotlyfe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import com.pdmcourse.spotlyfe.data.database.entities.PlaceEntity

@Dao
interface PlaceDao {
  @Query("SELECT * FROM places")
  fun getSavedPlaces(): Flow<List<PlaceEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun savePlace(place: PlaceEntity)
}