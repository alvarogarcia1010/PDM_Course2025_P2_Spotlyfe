package com.pdmcourse.spotlyfe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdmcourse.spotlyfe.ui.screens.Auth.Login.LoginScreen
import com.pdmcourse.spotlyfe.ui.screens.Auth.Register.RegisterScreen
import com.pdmcourse.spotlyfe.ui.screens.NewPlace.NewPlaceScreen
import com.pdmcourse.spotlyfe.ui.screens.SavedPlaces.SavedPlacesScreen

@Composable
fun MainNavigation(navController: NavHostController) {
  NavHost(navController = navController, startDestination = LoginScreenNavigation) {
    composable<LoginScreenNavigation> {
      LoginScreen(
        onLoginSuccess = { navController.navigate(SavedPlacesScreenNavigation) },
        onRegisterPressed = { navController.navigate(RegisterScreenNavigation) }
      )
    }
    composable<RegisterScreenNavigation> {
      RegisterScreen(
        onRegisterSuccess = { navController.navigate(SavedPlacesScreenNavigation) },
        onLoginPressed = { navController.navigate(LoginScreenNavigation) }
      )
    }
    composable<SavedPlacesScreenNavigation> {
      SavedPlacesScreen(
        onNewPlacePressed = { navController.navigate(NewPlaceScreenNavigation) }
      )
    }
    composable<NewPlaceScreenNavigation> {
      NewPlaceScreen(
        onBackPressed = { navController.popBackStack() }
      )
    }
  }
}