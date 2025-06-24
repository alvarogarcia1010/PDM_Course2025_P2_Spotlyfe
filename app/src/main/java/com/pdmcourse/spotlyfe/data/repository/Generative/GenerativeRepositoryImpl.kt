package com.pdmcourse.spotlyfe.data.repository.Generative

import com.google.firebase.ai.GenerativeModel
import com.pdmcourse.spotlyfe.helpers.Resource

class GenerativeRepositoryImpl(
  private val generativeModel:GenerativeModel
): GenerativeRepository {

    override suspend fun generateContent(prompt: String): Resource<String> {
      return try {
        val response = generativeModel.generateContent(prompt)

        val text = response.text

        if (text.isNullOrEmpty()) {
          Resource.Error("No content generated")
        } else {
          Resource.Success(text)
        }
      } catch (e: Exception) {
        Resource.Error("Failed to generate content: ${e.message}")
      }
    }
}