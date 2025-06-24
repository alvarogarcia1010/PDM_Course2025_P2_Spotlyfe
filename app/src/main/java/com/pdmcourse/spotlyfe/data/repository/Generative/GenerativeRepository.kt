package com.pdmcourse.spotlyfe.data.repository.Generative

import com.pdmcourse.spotlyfe.helpers.Resource

interface GenerativeRepository {
  suspend fun generateContent(prompt:String): Resource<String>
}