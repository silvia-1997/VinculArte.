package com.vincularte.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable


@Composable fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center )
    {
        Image(
            painter = painterResource(id = com.example.vincularte.R.drawable.fondo2),
            contentDescription = "Fondo de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column( modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center ) {

            Text( "¡VinculArte!",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp,fontWeight = FontWeight.Bold,),
                color = Color.Black, textAlign = TextAlign.Center )

            Spacer(modifier = Modifier.height(16.dp))

            Text( "Expresar lo que sentimos no nos hace débiles. Nos hace valientes. Es un acto de autenticidad, de conexión con nosotros mismos y con quienes nos rodean. Es permitirnos ser completos, sin miedo al juicio, sin vergüenza por sentir.",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.DarkGray,
                textAlign = TextAlign.Center )

            Spacer(modifier = Modifier.height(24.dp))

            Button( onClick = { navController.navigate("perfil") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA)),
                modifier = Modifier.fillMaxWidth() ) { Text("Ir al Perfil", fontSize = 18.sp, color = Color.White)

    }}}}