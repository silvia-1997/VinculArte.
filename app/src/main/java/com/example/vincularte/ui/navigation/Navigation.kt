import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vincularte.ui.screens.welcome.WelcomeScreen
import com.vincularte.ui.screens.register.RegisterScreen
import com.vincularte.ui.screens.home.HomeScreen
import com.vincularte.ui.screens.profile.ProfileScreen
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
        //composable("login") {LoginScreen(navController) } //
    }
}
