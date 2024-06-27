package tech.toshitworks.blogapp.presentation.sign_up

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RoundaboutLeft
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
import kotlinx.coroutines.flow.collectLatest
import tech.toshitworks.blogapp.domain.model.SignUpBody
import tech.toshitworks.blogapp.utils.Routes
import tech.toshitworks.blogapp.utils.SnackBarEvent

@Composable
fun SignUpPage(
    viewModel: SignUpViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val snackBarEvent = viewModel.snackBarEventFlow
    var signUpBody by remember {
        mutableStateOf(SignUpBody("", "", "", ""))
    }
    var nameError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var emailError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var passwordError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var aboutError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    nameError = when {
        state.signUpBody.name.isBlank() -> "Name should not be empty"
        state.signUpBody.name.length !in 5..15 -> "Name should contain a minimum of 5 characters and a maximum of 15 characters"
        else -> null
    }

    emailError = when {
        state.signUpBody.email.isBlank() -> "Email should not be empty"
        !state.signUpBody.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$")) -> "Email address is not valid"
        else -> null
    }

    passwordError = when {
        state.signUpBody.password.isBlank() -> "Password should not be empty"
        state.signUpBody.password.length !in 8..16 -> "Password should contain a minimum of 8 characters and a maximum of 16 characters"
        else -> null
    }
    aboutError = when {
        state.signUpBody.about.isBlank() -> "About should not be empty"
        state.signUpBody.about.length !in 20..100 -> "About should contain a minimum of 20 characters and a maximum of 100 characters"
        else -> null
    }
    LaunchedEffect(key1 = true) {
        snackBarEvent.collectLatest{
            println("in")
            when(it){
                is SnackBarEvent.ShowSnackBar -> {
                    println("hi")
                    snackBarHostState.showSnackbar(
                        message = it.message,
                        duration = it.duration
                    )
                    navController.navigate(Routes.LoginScreen.route)
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
                        text = "Sign Up",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        fontWeight = FontWeight.Bold
                    )
                    Crossfade(targetState = state.inAbout, label = "Switch") {
                        if (it) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp), // Ensure column fills width
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = signUpBody.about,
                                    onValueChange = { a ->
                                        signUpBody = signUpBody.copy(about = a)
                                        onEvent(SignUpEvents.OnSignUpBodyChange(signUpBody))
                                    },
                                    label = { Text(text = "About") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.RoundaboutLeft,
                                            contentDescription = "About"
                                        )
                                    },
                                    isError = aboutError != null && signUpBody.about.isNotBlank(),
                                    supportingText = {
                                        Text(text = aboutError.orEmpty())
                                    },
                                    maxLines = 6,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    ElevatedButton(
                                        onClick = { onEvent(SignUpEvents.OnBackClick) },
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Text(
                                            text = "Back",
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    ElevatedButton(
                                        onClick = { onEvent(SignUpEvents.OnSignUpClick) },
                                        modifier = Modifier.weight(1f),
                                        enabled = nameError == null && emailError == null && passwordError == null && aboutError == null
                                    ) {
                                        Text(
                                            text = "Sign Up",
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp), // Ensure column fills width
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = signUpBody.name,
                                    onValueChange = { n ->
                                        signUpBody = signUpBody.copy(name = n)
                                        onEvent(SignUpEvents.OnSignUpBodyChange(signUpBody))
                                    },
                                    label = { Text(text = "Name") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Name"
                                        )
                                    },
                                    supportingText = {
                                        Text(text = nameError.orEmpty())
                                    },
                                    isError = nameError != null && signUpBody.name.isNotBlank(),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = signUpBody.email,
                                    onValueChange = {
                                        signUpBody = signUpBody.copy(email = it)
                                        onEvent(SignUpEvents.OnSignUpBodyChange(signUpBody))
                                    },
                                    label = { Text(text = "Email") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Email,
                                            contentDescription = "Email"
                                        )
                                    },
                                    isError = emailError != null && signUpBody.email.isNotBlank(),
                                    supportingText = {
                                        Text(text = emailError.orEmpty())
                                    },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = signUpBody.password,
                                    onValueChange = {
                                        signUpBody = signUpBody.copy(password = it)
                                        onEvent(SignUpEvents.OnSignUpBodyChange(signUpBody))
                                    },
                                    label = { Text(text = "Password") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Password,
                                            contentDescription = "Password"
                                        )
                                    },
                                    isError = passwordError != null && signUpBody.password.isNotBlank(),
                                    supportingText = {
                                        Text(text = passwordError.orEmpty())
                                    },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                ElevatedButton(
                                    onClick = { onEvent(SignUpEvents.OnNextClick) },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = nameError == null && emailError == null && passwordError == null
                                ) {
                                    Text(
                                        text = "Next",
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.LoginScreen.route)
                            }
                            .padding(top = 8.dp),
                        text = "Already have an account? Goto Login Page.",
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
