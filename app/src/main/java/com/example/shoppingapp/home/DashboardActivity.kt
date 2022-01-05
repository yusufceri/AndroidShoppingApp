package com.example.shoppingapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import com.example.shoppingapp.base.SPABaseActivity
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.details.DetailsActivity
import com.example.shoppingapp.domain.RepoImpl
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes
import com.example.shoppingapp.shoppingcart.CartScreenActivity

class DashboardActivity : SPABaseActivity() {
    private val viewModel by viewModels<DashboardViewModel> { DashboardViewModelFactory(RepoImpl(DataSource())) }
    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    private var onNavigateOnDashboardCTA = { type: Dashboardctatypes ->
        when(type) {
            is Dashboardctatypes.navigateToCart ->
                startActivity(CartScreenActivity.newIntent(this))
            is Dashboardctatypes.navigateToDetails -> {
                if(!type.book.id.isNullOrEmpty())
                    startActivity(DetailsActivity.newIntent(this, type.book.id))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShoppingAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenWithTopBar(viewModel, onNavigateOnDashboardCTA)
                }
            }
        }
    }
}

