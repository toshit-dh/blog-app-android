package tech.toshitworks.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.toshitworks.blogapp.presentation.splash_screen.SplashScreenViewModel
import tech.toshitworks.blogapp.ui.theme.BlogAppTheme
import tech.toshitworks.blogapp.utils.NavGraphSetup
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition{
            !splashScreenViewModel.loadingState.value
        }
        setContent {
            BlogAppTheme {
                val screen by splashScreenViewModel.screenState
                val navController = rememberNavController()
                NavGraphSetup(navController = navController,screen = screen)
            }
        }
    }
}

