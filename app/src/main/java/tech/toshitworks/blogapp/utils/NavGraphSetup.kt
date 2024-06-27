package tech.toshitworks.blogapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.toshitworks.blogapp.presentation.categories.CategoryPage
import tech.toshitworks.blogapp.presentation.login.LoginPage
import tech.toshitworks.blogapp.presentation.login.LoginViewModel
import tech.toshitworks.blogapp.presentation.onboarding.OnBoardingPage
import tech.toshitworks.blogapp.presentation.sign_up.SignUpPage
import tech.toshitworks.blogapp.presentation.sign_up.SignUpViewModel

@Composable
fun NavGraphSetup(
    screen: String,
    navController: NavHostController
) {
    LaunchedEffect(key1 = true) {
        println("$screen nav")
    }
    NavHost(
        navController = navController, startDestination = screen
    ) {
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
            CategoryPage()
        }
    }
}