package com.pdmcourse.spotlyfe.ui.screens.Auth.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
  onLoginSuccess: () -> Unit = {},
  onRegisterPressed: () -> Unit = {},
) {
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  Scaffold { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp).fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "Iniciar sesión", fontSize = 28.sp)

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Correo electrónico") },
        modifier = Modifier.fillMaxWidth()
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
      )
      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = { onLoginSuccess() },
        enabled = true,
        modifier = Modifier
          .padding(top = 16.dp)
          .height(48.dp)
          .fillMaxWidth()
      ) {
        Text(text = "Iniciar sesión")
      }

      Spacer(modifier = Modifier.height(8.dp))

      TextButton(onClick = { onRegisterPressed() }) {
        Text(text = "¿No tienes cuenta? Regístrate aquí")
      }
    }
  }
}
