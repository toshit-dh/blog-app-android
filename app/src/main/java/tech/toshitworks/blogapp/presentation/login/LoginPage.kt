package tech.toshitworks.blogapp.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collect
import tech.toshitworks.blogapp.domain.model.LoginBody
import tech.toshitworks.blogapp.domain.model.SignUpBody
import tech.toshitworks.blogapp.presentation.sign_up.SignUpEvents
import tech.toshitworks.blogapp.utils.Routes
import tech.toshitworks.blogapp.utils.SnackBarEvent

@Composable
fun LoginPage(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val snackBarEvent = viewModel.snackBarEventFlow
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    var emailError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var passwordError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var loginBody by remember {
        mutableStateOf(LoginBody("", ""))
    }
    emailError = when {
        state.loginBody.email.isBlank() -> "Email should not be empty"
        !state.loginBody.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$")) -> "Email address is not valid"
        else -> null
    }

    passwordError = when {
        state.loginBody.password.isBlank() -> "Password should not be empty"
        state.loginBody.password.length !in 8..16 -> "Password should contain a minimum of 8 characters and a maximum of 16 characters"
        else -> null
    }
    LaunchedEffect(key1 = true) {
        snackBarEvent.collect{
            when(it){
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = it.message,
                        duration = it.duration
                    )
                    navController.popBackStack()
                    navController.navigate(Routes.CategoryScreen.route)
                }

                is SnackBarEvent.ShowPostSnackBar -> {

                }
            }
        }
    }
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer

                ),
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = loginBody.email,
                        onValueChange = {
                            loginBody = loginBody.copy(email = it)
                            onEvent(LoginEvents.OnLoginBodyChange(loginBody))
                        },
                        label = { Text(text = "Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email"
                            )
                        },
                        isError = emailError != null && loginBody.email.isNotBlank(),
                        supportingText = {
                            Text(text = emailError.orEmpty())
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = loginBody.password,
                        onValueChange = {
                            loginBody = loginBody.copy(password = it)
                            onEvent(LoginEvents.OnLoginBodyChange(loginBody))
                        },
                        label = { Text(text = "Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Password,
                                contentDescription = "Password"
                            )
                        },
                        isError = passwordError != null && loginBody.password.isNotBlank(),
                        supportingText = {
                            Text(text = passwordError.orEmpty())
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    ElevatedButton(
                        onClick = { onEvent(LoginEvents.OnLogin) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = emailError == null && passwordError == null
                    ) {
                        Text(
                            text = "Login",
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.SignUpScreen.route)
                            }
                            .padding(top = 8.dp),
                        text = "Don't have an account? Goto SignUp Page.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}