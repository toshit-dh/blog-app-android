package tech.toshitworks.blogapp.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tech.toshitworks.blogapp.R
import tech.toshitworks.blogapp.utils.Routes

@Composable
fun SplashScreenPage(
    viewModel: SplashScreenViewModel,
    navController: NavHostController
) {

    val state = viewModel.loadingState
    val screen = viewModel.screenState
    LaunchedEffect(key1 = state.value) {
        if(!state.value){
            navController.navigate(screen.value)
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Icon(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.blog_icon), contentDescription = "Blog Icon"
        )
    }
}