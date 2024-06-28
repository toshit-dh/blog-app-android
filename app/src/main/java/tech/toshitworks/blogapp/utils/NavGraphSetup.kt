package tech.toshitworks.blogapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.toshitworks.blogapp.presentation.categories.CategoryPage
import tech.toshitworks.blogapp.presentation.categories.CategoryViewModel
import tech.toshitworks.blogapp.presentation.home.HomePage
import tech.toshitworks.blogapp.presentation.home.HomeViewModel
import tech.toshitworks.blogapp.presentation.login.LoginPage
import tech.toshitworks.blogapp.presentation.login.LoginViewModel
import tech.toshitworks.blogapp.presentation.onboarding.OnBoardingPage
import tech.toshitworks.blogapp.presentation.sign_up.SignUpPage
import tech.toshitworks.blogapp.presentation.sign_up.SignUpViewModel
import tech.toshitworks.blogapp.presentation.splash_screen.SplashScreenPage
import tech.toshitworks.blogapp.presentation.splash_screen.SplashScreenViewModel

@Composable
fun NavGraphSetup(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Routes.SplashScreen.route
    ) {
        composable(Routes.SplashScreen.route){
            val viewModel: SplashScreenViewModel = hiltViewModel()
            SplashScreenPage(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.OnBoardingScreen.route){
            OnBoardingPage()
        }
        composable(Routes.SignUpScreen.route){
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpPage(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.LoginScreen.route){
            val viewModel: LoginViewModel = hiltViewModel()
            LoginPage(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.CategoryScreen.route){
            val viewModel: CategoryViewModel = hiltViewModel()
            CategoryPage(
                viewModel,
                navController
            )
        }
        composable(Routes.HomeScreen.route){
            val viewModel : HomeViewModel = hiltViewModel()
            HomePage(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}