package com.example.shoppingapp.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.login.loginctatypes.LoginCTATypes
import com.example.shoppingapp.utils.ShowError
import com.example.shoppingapp.utils.ShowProgress
import com.example.shoppingapp.vms.ResultStatus

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateOnLoginCTA: (LoginCTATypes) -> Unit
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    var loginClicked = remember { mutableStateOf(false) }

    if(loginClicked.value) {
        val loginState: State<ResultStatus<Any?>?> = viewModel?.sendCredentials.observeAsState()
        val loginStatus: ResultStatus<Any?>? = loginState.value

        when(loginStatus) {
            is ResultStatus.Success ->
               onNavigateOnLoginCTA(LoginCTATypes.navigateToDashboard())
            is ResultStatus.Loading ->
                ShowProgress()
            is ResultStatus.Failure -> {
                ShowError(loginStatus.exception)
                loginClicked.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shop_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 48.dp, height = 48.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
            )

        }
        Spacer(Modifier.size(30.dp))
        Text(text = stringResource(R.string.signin), fontSize = 20.sp)
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            isError = emailErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(R.string.enteremail))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(R.color.blue),
                focusedBorderColor = colorResource(R.color.blue))
        )
        if (emailErrorState.value) {
            Text(text = stringResource(R.string.required), color = Color.Red)
        }
        Spacer(Modifier.size(18.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it
            },
            isError = passwordErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(R.string.enterpassword))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(R.color.blue),
                focusedBorderColor = colorResource(R.color.blue)),
//            trailingIcon = {
//                IconButton(onClick = {
//                    passwordVisibility.value = !passwordVisibility.value
//                }) {
//                    Icon(
//                        imageVector = if (!passwordVisibility.value) Icons.Default.CheckCircle else Icons.Default.Warning,
//                        contentDescription = "visibility",
//                        tint = Color.Green
//                    )
//                }
//            },
            visualTransformation = PasswordVisualTransformation()
        )
        if (passwordErrorState.value) {
            Text(text = stringResource(R.string.required), color = Color.Red)
        }
        Spacer(Modifier.size(24.dp))
        Button(
            onClick = {
                when {
                    email.value.text.isEmpty() -> {
                        emailErrorState.value = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }
                    else -> {
                        passwordErrorState.value = false
                        emailErrorState.value = false
//                        Toast.makeText(
//                            context,
//                            "Logged in successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()

                        Log.d("LoginsScreen", "Login Clicked")
                        loginClicked.value = true
                        viewModel.login(email.value.text, password.value.text)

                    }
                }

            },
            content = {
                Text(text = stringResource(R.string.login))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.yellow))
        )
//        Spacer(Modifier.size(16.dp))
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            TextButton(onClick = {
//
//            }) {
//                Text(text = "Register ?", color = Color.Red)
//            }
//        }
    }
}











//@Composable
//fun LoginView(viewModel: LoginViewModel) {
//
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    val focusManager = LocalFocusManager.current
//    val user by viewModel.user.observeAsState()
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .background(Color.LightGray)
//            .fillMaxSize()
//            .clickable { focusManager.clearFocus() }
//    ) {
//        when (user?.status) {
//            Status.LOADING -> {
//                CircularProgressIndicator()
//            }
//            Status.SUCCESS -> {
//                if (user?.data == null) {
//                    LoginFields(
//                        email,
//                        password,
//                        onLoginClick = { viewModel.login("ttiganik@gmail.com", password) },
//                        onEmailChange = { email = it },
//                        onPasswordChange = { password = it }
//                    )
//                } else {
//                    Text("Login successful", fontSize = MaterialTheme.typography.h3.fontSize)
//                    Text(
//                        "User ${user?.data?.login}",
//                        fontSize = MaterialTheme.typography.h4.fontSize
//                    )
//                }
//            }
//            Status.ERROR -> {
//                val snackbarHostState = remember { SnackbarHostState() }
//                val coroutineScope = rememberCoroutineScope()
//
//                LoginFields(
//                    email,
//                    password,
//                    onLoginClick = { viewModel.login("ttiganik@gmail.com", password) },
//                    onEmailChange = { email = it },
//                    onPasswordChange = { password = it }
//                )
//
//                coroutineScope.launch {
//                    val result = snackbarHostState
//                        .showSnackbar(
//                            "Login error: ${user?.message}",
//                            actionLabel = "Dismiss",
//                            duration = SnackbarDuration.Indefinite
//                        )
//                    when (result) {
//                        SnackbarResult.ActionPerformed -> viewModel.goToInitialState()
//                    }
//                }
//
//                SnackbarHost(hostState = snackbarHostState)
//            }
//        }
//    }
//}

//@Composable
//fun LoginFields(
//    email: String,
//    password: String,
//    onEmailChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit,
//    onLoginClick: (String) -> Unit
//) {
//    val focusManager = LocalFocusManager.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp),
//        verticalArrangement = Arrangement.spacedBy(25.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Please login")
//
//        OutlinedTextField(
//            value = email,
//            placeholder = { Text(text = "user@email.com") },
//            label = { Text(text = "email") },
//            onValueChange = onEmailChange,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
//        )
//
//        OutlinedTextField(
//            value = password,
//            placeholder = { Text(text = "password") },
//            label = { Text(text = "password") },
//            onValueChange = onPasswordChange,
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
//        )
//
//        Button(onClick = {
//            if (email.isBlank() == false && password.isBlank() == false) {
//                onLoginClick(email)
//                focusManager.clearFocus()
//            } else {
//                Toast.makeText(
//                    context,
//                    "Please enter an email and password",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }) {
//            Text("Login")
//        }
//    }
//}