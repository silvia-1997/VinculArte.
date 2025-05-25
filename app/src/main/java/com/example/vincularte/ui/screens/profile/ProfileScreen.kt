package com.vincularte.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue("Usuario Anónimo")) }
    var bio by remember { mutableStateOf(TextFieldValue("Aquí puedes escribir algo sobre ti...")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Biografía") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            // TODO: Guardar cambios localmente o en backend si se añade
        }) {
            Text("Guardar cambios")
        }
    }
}
