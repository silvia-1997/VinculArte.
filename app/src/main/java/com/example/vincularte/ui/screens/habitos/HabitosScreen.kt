package com.example.vincularte.ui.screens.habitos

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.vincularte.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HabitosScreen(navController: NavHostController) {
    val context = LocalContext.current
    var newHabitName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())) }

    val habitsPerDate = remember { mutableStateMapOf<String, MutableList<HabitItem>>() }
    val habitList = remember { mutableStateListOf<HabitItem>() }

    // ✅ Cargar hábitos solo para la fecha seleccionada
    LaunchedEffect(selectedDate) {
        habitList.clear()
        habitList.addAll(habitsPerDate.getOrPut(selectedDate) { mutableListOf() })
    }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            profileImageUri = uri
        }
    }


    Image(
        painter = painterResource(id = R.drawable.fondo2),
        contentDescription = "Fondo de perfil",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )


        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bitácora de Hábitos", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Fecha: $selectedDate", fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDatePicker(context) { selectedDate = it } },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Seleccionar Fecha", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))



            LazyColumn {
                items(habitList) { habit ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = habit.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${habit.progress} veces",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        IconButton(onClick = {
                            val index = habitList.indexOf(habit)
                            if (index != -1) {
                                habitList[index] = habit.copy(progress = habit.progress + 1)
                                habitsPerDate[selectedDate] = habitList.toMutableList()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Sumar progreso")
                        }
                        IconButton(onClick = {
                            val index = habitList.indexOf(habit)
                            if (index != -1 && habit.progress > 0) {
                                habitList[index] = habit.copy(progress = habit.progress - 1)
                                habitsPerDate[selectedDate] = habitList.toMutableList()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Remove, contentDescription = "Restar progreso", tint = Color.Blue)
                        }
                        IconButton(
                            onClick = { habitList.remove(habit); habitsPerDate[selectedDate] = habitList.toMutableList() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar hábito", tint = Color.Red)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newHabitName,
                onValueChange = { newHabitName = it },
                label = { Text("Nueva actividad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newHabitName.isNotEmpty()) {
                        habitList.add(HabitItem(newHabitName, 0))
                        habitsPerDate[selectedDate] = habitList.toMutableList()
                        newHabitName = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Agregar actividad", fontSize = 18.sp, color = Color.White)
            }
        }
    }


// ✅ Modelo de datos para los hábitos
data class HabitItem(val name: String, var progress: Int)

// ✅ Función para mostrar el selector de fecha con `DatePickerDialog`
fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                Calendar.getInstance().apply {
                    set(year, month, day)
                }.time
            )
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
