package com.pdmcourse.spotlyfe.ui.screens.NewExperience

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse.spotlyfe.SpotLyfeApplication
import com.pdmcourse.spotlyfe.data.repository.Generative.GenerativeRepository

class NewExperienceViewModel(
  private val generativeRepository: GenerativeRepository
): ViewModel() {

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as SpotLyfeApplication)
        NewExperienceViewModel(application.appProvider.provideGenerativeRepository())
      }
    }
  }
}