package com.example.shoppingapp.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.shoppingapp.base.SPABaseActivity
import com.example.shoppingapp.checkout.checkoutctatypes.CheckoutCTATypes
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.domain.RepoImpl
import com.example.shoppingapp.home.MainActivity
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

class CheckoutActivity: SPABaseActivity() {
    private val viewModel by viewModels<CheckoutViewModel> { CheckoutViewModelFactory(
        RepoImpl(
            DataSource()
        )
    ) }

    private fun navigationCTA(checkoutCTAType: CheckoutCTATypes) {
        when (checkoutCTAType) {
            is CheckoutCTATypes.placeOrder -> {
                viewModel.orderConfirmed()
            }
            is CheckoutCTATypes.navigateToDashboard -> {
                startActivity(MainActivity.newIntent(this))
            }
        }
    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, CheckoutActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenWithTopBar(viewModel) {
                        navigationCTA(checkoutCTAType = it)
                    }
                }
            }
        }
    }
}