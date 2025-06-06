package com.example.vincularte.ui.screens.fotos

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.rememberAsyncImagePainter
import com.example.vincularte.R

@Composable
fun FotosScreen() {
    var imageList by remember { mutableStateOf(mutableListOf<Uri>()) }
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imageList.add(uri)
        }
    }

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Galería de Fotos", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Botón para subir nueva imagen
        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
        ) {
            Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = "Subir foto")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Subir foto", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Grid de imágenes
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(imageList.size) { index ->
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(imageList[index]),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { imageList.removeAt(index) }, // ✅ Eliminar imagen
                        modifier = Modifier.align(Alignment.TopEnd)

                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar foto", tint = Color.Red)
                    }
                }
            }
        }
    }
}
