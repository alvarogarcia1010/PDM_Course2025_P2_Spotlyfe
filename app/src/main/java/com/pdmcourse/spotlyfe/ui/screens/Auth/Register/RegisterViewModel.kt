package com.pdmcourse.spotlyfe.ui.screens.Auth.Register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse.spotlyfe.SpotLyfeApplication
import com.pdmcourse.spotlyfe.data.repository.Auth.AuthRepository
import com.pdmcourse.spotlyfe.helpers.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading : StateFlow<Boolean> = _loading

  fun registerUser(email: String, password: String, onRegisterSuccess: () -> Unit) = viewModelScope.launch {
    authRepository.registerUser(email = email, password = password).collectLatest { result ->
      when(result) {
        is Resource.Loading -> {
          _loading.value = true
        }

        is Resource.Success -> {
          Log.d("Auth", "Registration successful: ${result.data?.user?.uid}")
          _loading.value = false
          onRegisterSuccess()
        }

        is Resource.Error -> {
          _loading.value = false
        }
      }
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as SpotLyfeApplication)
        RegisterViewModel(application.appProvider.provideAuthRepository())
      }
    }
  }
}