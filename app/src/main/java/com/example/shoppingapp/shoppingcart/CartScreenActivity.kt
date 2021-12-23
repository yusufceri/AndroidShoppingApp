package com.example.shoppingapp.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.shoppingapp.checkout.CheckoutActivity
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.domain.RepoImpl
import com.example.shoppingapp.home.MainActivity
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

class CartScreenActivity: ComponentActivity() {
    private val viewModel by viewModels<CartViewModel> { CartViewModelFactory(
        RepoImpl(
            DataSource()
        )
    ) }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, CartScreenActivity::class.java)
    }

    private fun navigationCTA(cartCTAType: CartCTATypes) {
        when (cartCTAType) {
            is CartCTATypes.removeFromCart -> {

            }
            is CartCTATypes.ProceedToCheckout -> {
                startActivity(CheckoutActivity.newIntent(this))
            }
            is CartCTATypes.navigateToCart -> {
                startActivity(CartScreenActivity.newIntent(this))
            }
            is CartCTATypes.navigatetoDashboard -> {
                startActivity(MainActivity.newIntent(this))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShoppingAppTheme {
                CartContentWithTopBar(viewModel) {
                    navigationCTA(cartCTAType = it)
                }
            }
        }
    }
}