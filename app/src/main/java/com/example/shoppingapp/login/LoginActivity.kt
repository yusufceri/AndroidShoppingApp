package com.example.shoppingapp.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.shoppingapp.base.ViewModelFactory
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.domain.RepoImpl
import com.example.shoppingapp.home.DashboardActivity
import com.example.shoppingapp.login.loginctatypes.LoginCTATypes
import com.example.shoppingapp.login.ui.theme.ShoppingAppTheme

class LoginActivity : ComponentActivity() {

    private val viewModel by viewModels<LoginViewModel> { ViewModelFactory(
        RepoImpl(
            DataSource()
        )
    ) }

    private fun navigationCTA(loginCTAType: LoginCTATypes) {
        when (loginCTAType) {
            is LoginCTATypes.navigateToDashboard -> {
                startActivity(DashboardActivity.newIntent(this))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var isSplashscreenPresented by remember { mutableStateOf(false) }
                    val isSplashscreenPresentedState = viewModel.setSplashScreen().observeAsState<Boolean>()
                    isSplashscreenPresented = isSplashscreenPresentedState.value ?: false
                    if(isSplashscreenPresented) {
                        LoginScreen(viewModel) { loginCTATypes ->
                            navigationCTA(loginCTATypes)
                        }
                    }
                }
            }
        }
    }
}