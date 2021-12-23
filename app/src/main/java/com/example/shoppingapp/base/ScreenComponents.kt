//package com.example.shoppingapp.base
//
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.unit.dp
//import com.example.shoppingapp.R
//import com.example.shoppingapp.data.model.Book
//import com.example.shoppingapp.home.HomeViewModel
//import com.example.shoppingapp.home.ScreenContent
//import com.example.shoppingapp.base.callbacks.CTATypes
//import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes
//
//@Composable
//fun ScreenWithTopBar(
//    viewModel: HomeViewModel,
//    onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit
//) {
//    val context = LocalContext.current
//    // theme for our app.
//    Scaffold(
//        // below line we are
//        // creating a top bar.
//        topBar = {
//            TopAppBar(
//                // in below line we are
//                // adding title to our top bar.
//                title = {
//                    // inside title we are
//                    // adding text to our toolbar.
//                    Text(
//                        text = context.getString(R.string.app_name),
//                        // below line is use
//                        // to give text color.
//                        color = Color.White
//                    )
//                },
////                navigationIcon = {
////                    // navigation icon is use
////                    // for drawer icon.
////                    IconButton(onClick = { }) {
////                        // below line is use to
////                        // specify navigation icon.
////                        Icon(Icons.Filled.ArrowBack, "")
////                    }
////                },
//                actions = {
//                    //Navigate to cart
//                    IconButton(onClick = {
//                        onNavigateOnDashboardCTA(Dashboardctatypes.navigateToCart())
//                    }) {
//                        // below line is use to
//                        // specify navigation icon.
//                        Icon(Icons.Filled.ShoppingCart, "")
//                    }
//                },
//
//                // below line is use to give background color
//                backgroundColor = colorResource(id = R.color.blue),
//
//                // content color is use to give
//                // color to our content in our toolbar.
//                contentColor = Color.White,
//
//                // below line is use to give
//                // elevation to our toolbar.
//                elevation = 12.dp
//            )
//        }, content = {
//            ScreenContent(viewModel = viewModel, onNavigateOnDashboardCTA)
//        })
//}