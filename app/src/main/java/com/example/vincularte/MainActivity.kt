package com.example.vincularte
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.vincularte.ui.theme.VinculArteTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import com.example.vincularte.ui.screens.profile.ProfileScreen
import com.vincularte.ui.screens.diary.DiaryScreen
import com.vincularte.ui.screens.home.HomeScreen
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import com.example.vincularte.ui.screens.fotos.FotosScreen
import com.example.vincularte.ui.screens.habitos.HabitosScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinculArteTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("perfil") { ProfileScreen(navController) }
                    composable("diary") { DiaryScreen(navController) }
                    composable("habit") { HabitosScreen(navController) }
                    composable(route = "fotos") { FotosScreen() }
                    }
                }
            }
        }
    }

    @Composable
    fun WelcomeScreen(navController: NavHostController) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo),
                contentDescription = "Fondo de bienvenida",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )

            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "¡Bienvenido a VinculArte!",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp,fontWeight = FontWeight.Bold,),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Creemos que el bienestar emocional es el primer paso hacia una vida plena. Nuestra aplicación te acompaña con herramientas accesibles y humanas para fortalecer tu salud mental, fomentar la autocompasión y reconectar con lo que realmente importa: tú.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA)), // 🎨 Azul claro
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Siéntete Seguro", fontSize = 18.sp, color = Color.Black)
                }
            }
        }
    }


@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // 🔹 Solo obtendrá el correo del usuario, no autenticar en Firebase
                .build()
        )
    }

    val googleLoginLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val email = account.email ?: "No se obtuvo el correo"
                val displayName = account.displayName ?: "Nombre desconocido"

                Log.i("GoogleSignIn", "Usuario: $displayName, Email: $email")

                navController.navigate("home") // ✅ Redirige a home tras login exitoso
            } else {
                loginError = "No se pudo obtener la cuenta de Google"
            }
        } catch (e: ApiException) {
            loginError = "Google Sign-In falló: ${e.message}"
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = "Fondo de Login",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Iniciar sesión", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Correo electrónico o usuario") },
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

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Iniciar sesión", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Crear cuenta", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ Botón para iniciar sesión con Google sin Web Client ID
            Button(
                onClick = { googleLoginLauncher.launch(googleSignInClient.signInIntent) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Google Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ingresar con cuenta Google", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            loginError?.let {
                Text(it, fontSize = 14.sp, color = Color.Red)
            }
        }
    }
}




@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var cartaCompromisoNombre by remember { mutableStateOf("Ningún archivo seleccionado") }
    var identificacionNombre by remember { mutableStateOf("Ningún archivo seleccionado") }
    var rostroEscaneado by remember { mutableStateOf(false) }

    var aceptaTerminos by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = "Fondo de Registro",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Registro",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Carta compromiso
            Text("Carta compromiso", fontWeight = FontWeight.SemiBold)
            Button(
                onClick = {
                    cartaCompromisoNombre = "carta_compromiso.pdf"
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Adjuntar archivo")
            }
            Text(cartaCompromisoNombre, fontSize = 12.sp, color = Color.DarkGray)

            Spacer(modifier = Modifier.height(16.dp))

            // Identificación oficial
            Text("Identificación oficial", fontWeight = FontWeight.SemiBold)
            Button(
                onClick = {
                    identificacionNombre = "foto_ine_frontal.jpg"
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Adjuntar foto")
            }
            Text(identificacionNombre, fontSize = 12.sp, color = Color.DarkGray)

            Spacer(modifier = Modifier.height(16.dp))

            // Escaneo de rostro
            Button(
                onClick = {
                    rostroEscaneado = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Escanear rostro", color = Color.White)
            }
            if (rostroEscaneado) {
                Text("Rostro escaneado correctamente ✅", fontSize = 12.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Checkbox de Términos y Condiciones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )
                Text(
                    "Acepto los términos y condiciones",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 14.sp
                )
            }

            if (mostrarError && !aceptaTerminos) {
                Text(
                    "Debes aceptar los términos y condiciones.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (aceptaTerminos) {
                        navController.navigate("home")
                    } else {
                        mostrarError = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CCBDA))
            ) {
                Text("Confirmar Registro", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}


