package com.pdmcourse.spotlyfe.ui.screens.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse.spotlyfe.SpotLyfeApplication
import com.pdmcourse.spotlyfe.data.repository.Auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

  private val _loading = MutableStateFlow(true)
  val loading: StateFlow<Boolean> = _loading

  val isLoggedIn: StateFlow<Boolean> = authRepository.isLoggedIn()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = false
    )

  init {
    viewModelScope.launch {
      isLoggedIn.collect {
        _loading.update { false }
      }
    }
  }

  fun logout() {
    authRepository.logout()
  }

  companion object {
    val Factory : ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as SpotLyfeApplication)

        AuthViewModel(
          application.appProvider.provideAuthRepository(),
        )
      }
    }
  }
}