package com.vincularte.ui.screens.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vincularte.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DiaryScreen(navController: NavHostController) {
    var selectedEmoji by remember { mutableStateOf("😊") }
    var moodDescription by remember { mutableStateOf(TextFieldValue("")) }
    val savedMoods = remember { mutableStateListOf<Pair<String, String>>() }
    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    Image(
        painter = painterResource(id = R.drawable.fondo2),
        contentDescription = "Fondo de perfil",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // ✅ Fecha
        Text(
            text = "Fecha: $currentDate",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Selección de emojis
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("😊", "😢", "😠", "😐", "❤️").forEach { emoji ->
                Text(
                    text = emoji,
                    fontSize = 24.sp,
                    modifier = Modifier.clickable { selectedEmoji = emoji }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Descripción del estado de ánimo
        OutlinedTextField(
            value = moodDescription,
            onValueChange = { moodDescription = it },
            label = { Text("Describe tu estado de ánimo") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Botón para guardar el estado de ánimo
        Button(
            onClick = {
                if (moodDescription.text.isNotEmpty()) {
                    savedMoods.add(selectedEmoji to moodDescription.text)
                    moodDescription = TextFieldValue("") // Limpia el campo después de guardar
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
        ) {
            Text("Guardar estado de ánimo", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Lista de estados de ánimo guardados
        LazyColumn {
            items(savedMoods) { mood ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${mood.first} - ${mood.second}",
                        fontSize = 16.sp
                    )
                    IconButton(
                        onClick = { savedMoods.remove(mood) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
