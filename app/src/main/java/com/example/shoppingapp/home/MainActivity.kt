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

class MainActivity : SPABaseActivity() {
    private val viewModel by viewModels<HomeViewModel> { HomeViewModelFactory(RepoImpl(DataSource())) }

//    lateinit var CTANavigation: (type: Dashboardctatypes) -> Unit

    companion object {
        fun newIntent(context: Context) =
            Intent(context, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    private var onNavigateOnDashboardCTA = { type: Dashboardctatypes ->
        when(type) {
            is Dashboardctatypes.navigateToCart ->
//                Log.d("Dashboard","NAvigate To Cart")
                //TODO
                startActivity(CartScreenActivity.newIntent(this))
            is Dashboardctatypes.navigateToDetails -> {
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
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    MyApp(viewModel) {
//                        startActivity(DetailsActivity.newIntent(this, it))
//                    }
                    ScreenWithTopBar(viewModel, onNavigateOnDashboardCTA )
//                    {
//                        startActivity(DetailsActivity.newIntent(this, it))
//                    }
                }
            }
        }
    }

//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        ShoppingAppTheme {
//            ScreenWithTopBar(viewModel,  onNavigateOnDashboardCTA)
//        }
//    }
}

