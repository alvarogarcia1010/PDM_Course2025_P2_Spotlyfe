package com.pdmcourse.spotlyfe.ui.screens.Auth.Login

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

class LoginViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading : StateFlow<Boolean> = _loading

  fun login(email : String,password : String, onLoginSuccess: () -> Unit) = viewModelScope.launch {
    authRepository.loginUser(email = email, password = password).collectLatest { result ->
      when(result) {
        is Resource.Loading -> {
          _loading.value = true
        }

        is Resource.Success -> {
          Log.d("Auth", "Login successful: ${result.data?.user?.uid}")
          _loading.value = false
          onLoginSuccess()
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
        LoginViewModel(application.appProvider.provideAuthRepository())
      }
    }
  }
}