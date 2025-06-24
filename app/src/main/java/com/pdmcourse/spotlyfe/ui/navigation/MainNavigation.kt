package com.pdmcourse.spotlyfe.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdmcourse.spotlyfe.ui.screens.Auth.AuthViewModel
import com.pdmcourse.spotlyfe.ui.screens.Auth.Login.LoginScreen
import com.pdmcourse.spotlyfe.ui.screens.Auth.Register.RegisterScreen
import com.pdmcourse.spotlyfe.ui.screens.NewExperience.NewExperienceScreen
import com.pdmcourse.spotlyfe.ui.screens.NewPlace.NewPlaceScreen
import com.pdmcourse.spotlyfe.ui.screens.SavedPlaces.SavedPlacesScreen

@Composable
fun MainNavigation(navController: NavHostController, authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)) {
  val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
  val loading by authViewModel.loading.collectAsState()

  if (loading) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator()
    }

    return
  }

  val startDestination = if (isLoggedIn) SavedPlacesScreenNavigation else LoginScreenNavigation

  NavHost(navController = navController, startDestination = startDestination) {
    composable<LoginScreenNavigation> {
      LoginScreen(
        onLoginSuccess = { navController.navigate(SavedPlacesScreenNavigation) {
          popUpTo(LoginScreenNavigation) { inclusive = true }
        } },
        onRegisterPressed = { navController.navigate(RegisterScreenNavigation) }
      )
    }
    composable<RegisterScreenNavigation> {
      RegisterScreen(
        onRegisterSuccess = { navController.navigate(SavedPlacesScreenNavigation) {
          popUpTo(RegisterScreenNavigation) { inclusive = true }
        } },
        onLoginPressed = { navController.navigate(LoginScreenNavigation) }
      )
    }
    composable<SavedPlacesScreenNavigation> {
      SavedPlacesScreen(
        onNewPlacePressed = { navController.navigate(NewPlaceScreenNavigation) },
        onNewExperiencePressed = { navController.navigate(NewExperienceScreenNavigation) },
        onLogoutPressed = {
          authViewModel.logout()
          navController.navigate(LoginScreenNavigation) {
            popUpTo(SavedPlacesScreenNavigation) { inclusive = true }
          }
        }
      )
    }
    composable<NewPlaceScreenNavigation> {
      NewPlaceScreen(
        onBackPressed = { navController.popBackStack() }
      )
    }
    composable<NewExperienceScreenNavigation> {
      NewExperienceScreen(
        onBackPressed = { navController.popBackStack() }
      )
    }
  }
}