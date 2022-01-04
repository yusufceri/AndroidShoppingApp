package com.example.shoppingapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.shoppingapp.base.SPABaseActivity
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.details.DetailsActivity
import com.example.shoppingapp.domain.RepoImpl
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes
import com.example.shoppingapp.shoppingcart.CartScreenActivity

class DashboardActivity : SPABaseActivity() {
    private val viewModel by viewModels<DashboardViewModel> { DashboardViewModelFactory(RepoImpl(DataSource())) }

//    lateinit var CTANavigation: (type: Dashboardctatypes) -> Unit

    companion object {
        fun newIntent(context: Context) =
            Intent(context, DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    private var onNavigateOnDashboardCTA = { type: Dashboardctatypes ->
        when(type) {
            is Dashboardctatypes.navigateToCart ->
//                Log.d("Dashboard","NAvigate To Cart")
                //TODO
                startActivity(CartScreenActivity.newIntent(this))
            is Dashboardctatypes.navigateToDetails -> {
                if(!type.book.id.isNullOrEmpty())
                    startActivity(DetailsActivity.newIntent(this, type.book.id))
//                Log.d("Dashboard", "bookId = " + type.book.id)
            }
        }

//        Log.d("MAinScreen", "NAvigateToCart")
//        val book: Book = Book(id = 1, author = "", price = 12.0, title = "")
        //startActivity(DetailsActivity.newIntent(this, book))
    }

//    private var onNavigateToCart = { type: Dashboardctatypes ->
//        when(type) {
//            is Dashboardctatypes.navigateToCart -> 12
//                //TODO
//            is Dashboardctatypes.navigateToDetails ->
//                type.book
//
//                startActivity(DetailsActivity.newIntent(this, Dashboardctatypes.navigateToDetails.))
//        }
//
//        Log.d("MAinScreen", "NAvigateToCart")
//        val book: Book = Book(id = 1, author = "", price = "", title = "")
//        //startActivity(DetailsActivity.newIntent(this, book))
//    }
//    private var onNavigateToDetails = { book: Book ->
//        startActivity(DetailsActivity.newIntent(this, book.id))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShoppingAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    var isSplashscreenPresented by remember { mutableStateOf(false) }
                    val isSplashscreenPresentedState = viewModel.setSplashScreen().observeAsState<Boolean>()
                    isSplashscreenPresented = isSplashscreenPresentedState.value ?: false
                    Log.d("DashboardActivity", "Splash Screen === $isSplashscreenPresented")
                    if(isSplashscreenPresented) {
                        ScreenWithTopBar(viewModel, onNavigateOnDashboardCTA)
                    }
                }
            }
        }
    }
}

