package tech.toshitworks.blogapp.utils


sealed class Routes(val route: String) {
    data object OnBoardingScreen: Routes("OnBoardingScreen")
    data object SplashScreen: Routes("SplashScreen")
    data object SignUpScreen: Routes("SignUp")
    data object LoginScreen: Routes("Login")
    data object HomeScreen: Routes("HomeScreen")
    data object ProfileScreen: Routes("ProfileScreen")
    data object PostScreen: Routes("PostScreen")
    data object AddPostScreen: Routes("AddPostScreen")
    data object CategoryScreen: Routes("CategoryScreen")
}