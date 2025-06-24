package com.pdmcourse.spotlyfe.ui.screens.NewExperience

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse.spotlyfe.ui.layout.CustomTopBar

@Composable
fun NewExperienceScreen(
  viewModel: NewExperienceViewModel = viewModel(factory = NewExperienceViewModel.Factory),
  onBackPressed: (() -> Unit) = {}
) {
  val scrollState = rememberScrollState()
  val loading by viewModel.loading.collectAsState()
  val myExperience by viewModel.myExperience.collectAsState()

  Scaffold(
    topBar = {
      CustomTopBar(
        title = "New Experience",
        onBackPressed = onBackPressed
      )
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp).verticalScroll(scrollState),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (loading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
      } else {
        Text(text = myExperience, fontSize = 18.sp)
      }
    }
  }
}