package com.pdmcourse.spotlyfe.ui.screens.NewExperience

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
  Scaffold(
    topBar = {
      CustomTopBar(
        title = "New Experience",
        onBackPressed = onBackPressed
      )
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp).fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "Mi nueva experiencia", fontSize = 28.sp)
    }
  }
}