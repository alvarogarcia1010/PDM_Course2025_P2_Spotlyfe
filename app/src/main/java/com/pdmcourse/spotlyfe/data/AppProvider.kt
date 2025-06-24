package com.pdmcourse.spotlyfe.data

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.auth.FirebaseAuth
import com.pdmcourse.spotlyfe.data.database.AppDatabase
import com.pdmcourse.spotlyfe.data.repository.Auth.AuthRepository
import com.pdmcourse.spotlyfe.data.repository.Auth.AuthRepositoryImpl
import com.pdmcourse.spotlyfe.data.repository.Generative.GenerativeRepository
import com.pdmcourse.spotlyfe.data.repository.Generative.GenerativeRepositoryImpl
import com.pdmcourse.spotlyfe.data.repository.Place.PlaceRepository
import com.pdmcourse.spotlyfe.data.repository.Place.PlaceRepositoryImpl

class AppProvider(context: Context) {
  private val appDatabase = AppDatabase.getDatabase(context)
  private val placeDao = appDatabase.placeDao()
  private val placeRepository: PlaceRepository = PlaceRepositoryImpl(placeDao)

  private val firebaseAuthInstance: FirebaseAuth = FirebaseAuth.getInstance()
  private val authRepository: AuthRepository = AuthRepositoryImpl(firebaseAuthInstance)

  private val generativeModel = Firebase.ai(backend = GenerativeBackend.vertexAI()).generativeModel("gemini-2.5-flash")
  private val generativeRepository: GenerativeRepository = GenerativeRepositoryImpl(generativeModel)

  fun providePlaceRepository() : PlaceRepository {
    return placeRepository
  }

  fun provideAuthRepository() : AuthRepository {
    return authRepository
  }

  fun provideGenerativeRepository() : GenerativeRepository {
    return generativeRepository
  }
}