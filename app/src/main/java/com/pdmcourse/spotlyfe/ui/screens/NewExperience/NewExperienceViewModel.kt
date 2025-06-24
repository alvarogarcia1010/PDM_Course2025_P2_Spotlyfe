package com.pdmcourse.spotlyfe.ui.screens.NewExperience

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse.spotlyfe.SpotLyfeApplication
import com.pdmcourse.spotlyfe.data.repository.Generative.GenerativeRepository
import com.pdmcourse.spotlyfe.helpers.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewExperienceViewModel(
  private val generativeRepository: GenerativeRepository
): ViewModel() {

  private val _myExperience = MutableStateFlow<String>("")
  val myExperience : StateFlow<String> = _myExperience

  private val _loading = MutableStateFlow<Boolean>(true)
  val loading : StateFlow<Boolean> = _loading

  init {
    generateExperience()
  }

  fun generateExperience() {
    viewModelScope.launch {
      val prompt = generatePrompt()
      val result = generativeRepository.generateContent(prompt)

      when (result) {
        is Resource.Success -> {
          _myExperience.value = result.data ?: "No experience generated"
          _loading.value = false
        }
        is Resource.Error -> {
          _myExperience.value = "No experience generated"
          _loading.value = false
        }
        is Resource.Loading -> {
          _loading.value = true
        }
      }
    }
  }

  private fun generatePrompt(): String {
    // 1. Asume que tienes una forma de obtener los lugares del usuario.
    val favoritePlaces = arrayOf("El Café de Don Pedro", "Librería UCA", "Parque Bicentenario")
    val foodiePlaces = arrayOf("Playa El cuco", "Metrocentro Lourdes", "Suchitoto")
    val naturePlaces = arrayOf("Parque Nacional Cerro Verde", "Lago de Coatepeque", "Reserva Natural El Imposible")

    // 2. Formatea la lista para el prompt.
    val placesString = foodiePlaces.joinToString(", ")

    // 3. ¡El Prompt Mágico!
    val prompt = """
        Actúa como un guía turístico experto para El Salvador, eres amigable y creativo.
        Basándote en la siguiente lista de lugares favoritos de un usuario: "$placesString".

        Tu tarea es:
        1.  Inferir rápidamente los gustos del usuario (ej: "le gustan los cafés", "prefiere la naturaleza").
        2.  Darle una breve y cálida introducción basada en lo que inferiste.
        3.  Recomendarle **exactamente 3 lugares nuevos** que le encantarán.
        4.  Para cada lugar, escribe solo su nombre en negrita y una sola frase explicando por qué es una buena recomendación para él.

        Genera la respuesta.
    """.trimIndent()

    return prompt
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as SpotLyfeApplication)
        NewExperienceViewModel(application.appProvider.provideGenerativeRepository())
      }
    }
  }
}