package com.pdmcourse.spotlyfe.data.repository.Auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.pdmcourse.spotlyfe.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

  fun isLoggedIn(): Flow<Boolean>

  fun getCurrentUser(): Flow<Resource<FirebaseUser>>

  fun getCurrentToken(): Flow<Resource<String>>

  fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

  fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>

  fun logout()
}