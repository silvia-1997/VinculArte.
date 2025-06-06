package com.vincularte.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }
    var registroExitoso by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("users")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registro", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )
                Text("Acepto los términos y condiciones", modifier = Modifier.padding(start = 8.dp))
            }

            if (mostrarError && !aceptaTerminos) {
                Text("Debes aceptar los términos y condiciones.", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (aceptaTerminos) {
                        registerUser(email, password, username) { success ->
                            if (success) {
                                registroExitoso = true
                                navController.navigate("home")
                            } else {
                                mostrarError = true
                            }
                        }
                    } else {
                        mostrarError = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Confirmar Registro", fontSize = 18.sp, color = Color.White)
            }

            if (registroExitoso) {
                Text("Registro exitoso ✅", fontSize = 14.sp, color = Color.Green)
            }
        }
    }
}

// ✅ Función para registrar usuarios y guardarlos en Firebase
fun registerUser(email: String, password: String, name: String, onResult: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("users")

    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = User(
                uid = auth.currentUser?.uid ?: "",
                name = name,
                email = email,
                creationDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            )
            database.child(user.uid).setValue(user).addOnCompleteListener { dbTask ->
                onResult(dbTask.isSuccessful)
            }
        } else {
            onResult(false)
        }
    }
}

// ✅ Modelo de datos para los usuarios
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val creationDate: String = ""
)
