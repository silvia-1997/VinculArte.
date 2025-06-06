package com.example.vincularte.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vincularte.ui.screens.fotos.FotosScreen
import com.example.vincularte.ui.screens.habitos.HabitosScreen
import com.example.vincularte.ui.screens.profile.ProfileScreen
import com.vincularte.ui.screens.welcome.WelcomeScreen
import com.vincularte.ui.screens.register.RegisterScreen
import com.vincularte.ui.screens.home.HomeScreen
import com.vincularte.ui.screens.diary.DiaryScreen
import com.vincularte.ui.screens.help.HelpScreen
import com.vincularte.ui.screens.community.CommunityScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("diary") { DiaryScreen(navController) }
        composable("help") { HelpScreen(navController) }
        composable("community") { CommunityScreen(navController) }
        composable("habitos") { HabitosScreen(navController) }
        composable(route = "fotos") { FotosScreen() }
        //composable("login") {LoginScreen(navController) } //
    }
}
