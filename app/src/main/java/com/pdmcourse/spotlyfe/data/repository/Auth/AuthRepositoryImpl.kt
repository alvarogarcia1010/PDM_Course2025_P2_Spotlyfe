package com.pdmcourse.spotlyfe.data.repository.Auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pdmcourse.spotlyfe.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
  private val authService: FirebaseAuth
): AuthRepository {

  override fun isLoggedIn(): Flow<Boolean> = flow {
    val currentUser = authService.currentUser
    emit(currentUser != null)
  }

  override fun getCurrentUser(): Flow<Resource<FirebaseUser>> = flow {
    val currentUser = authService.currentUser
    if (currentUser != null) {
      emit(Resource.Success(currentUser))
    } else {
      emit(Resource.Error("No user is currently signed in"))
    }
  }

  override fun getCurrentToken(): Flow<Resource<String>> = flow {
    val currentUser = authService.currentUser
    if (currentUser != null) {
      try {
        val token = currentUser.getIdToken(true).await().token
        if (token != null) {
          emit(Resource.Success(token))
        } else {
          emit(Resource.Error("Failed to retrieve token"))
        }
      } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "Failed to retrieve token"))
      }
    } else {
      emit(Resource.Error("No user is currently signed in"))
    }
  }

  override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
    if (email.isEmpty() || password.isEmpty()) {
      emit(Resource.Error("Email or password can't be empty"))
      return@flow
    }

    emit(Resource.Loading())
    try {
      val result = authService.signInWithEmailAndPassword(email, password).await()
      emit(Resource.Success(result))
    } catch (e: Exception) {
      emit(Resource.Error(e.message ?: "Login failed"))
    }
  }

  override fun registerUser(email: String, password: String) : Flow<Resource<AuthResult>> = flow {
    if (email.isEmpty() || password.isEmpty()) {
      emit(Resource.Error("Email or password can't be empty"))
      return@flow
    }

    emit(Resource.Loading())
    try {
      val result = authService.createUserWithEmailAndPassword(email, password).await()
      emit(Resource.Success(result))
    } catch (e: Exception) {
      emit(Resource.Error(e.message ?: "Registration failed"))
    }
  }

  override fun logout() {
    authService.signOut()
  }
}