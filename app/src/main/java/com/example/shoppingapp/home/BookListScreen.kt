package com.example.shoppingapp.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.BookItem
import com.example.shoppingapp.data.model.BookList
import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes
import com.example.shoppingapp.utils.*
import com.example.shoppingapp.vms.ResultStatus

@Composable
fun ScreenWithTopBar(
    viewModel: DashboardViewModel,
    onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit
) {
    val context = LocalContext.current
    // theme for our app.
    Scaffold(
        // below line we are
        // creating a top bar.
        topBar = {
            TopAppBar(
                // in below line we are
                // adding title to our top bar.
                title = {
                    // inside title we are
                    // adding text to our toolbar.
                    Text(
                        text = context.getString(R.string.app_name),
                        // below line is use
                        // to give text color.
                        color = Color.White
                    )
                },
//                navigationIcon = {
//                    // navigation icon is use
//                    // for drawer icon.
//                    IconButton(onClick = { }) {
//                        // below line is use to
//                        // specify navigation icon.
//                        Icon(Icons.Filled.ArrowBack, "")
//                    }
//                },
                actions = {
                    //Navigate to cart
                    IconButton(onClick = {
                        onNavigateOnDashboardCTA(Dashboardctatypes.navigateToCart())
                    }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(Icons.Filled.ShoppingCart, "")
                    }
                },

                // below line is use to give background color
                backgroundColor = colorResource(id = R.color.blue),

                // content color is use to give
                // color to our content in our toolbar.
                contentColor = Color.White,

                // below line is use to give
                // elevation to our toolbar.
                elevation = 12.dp
            )
        }, content = {
            ScreenContent(viewModel = viewModel, onNavigateOnDashboardCTA)
        })
}

@Composable
fun ScreenContent(
    viewModel: DashboardViewModel,
    onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit
) {
    MyApp(viewModel, onNavigateOnDashboardCTA)
}

@Composable
fun MyApp(
    viewModel: DashboardViewModel,
    onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit
) {
    Scaffold(
        content = {
            BookListContent(viewModel, onNavigateOnDashboardCTA)
        }
    )
}

@Composable
fun BookListContent(
    viewModel: DashboardViewModel,
    onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit
) {
    val bookListResultState: State<ResultStatus<Any>?> = viewModel.fetchAllBooks.observeAsState<ResultStatus<Any>>()
    val bookListResult: ResultStatus<Any>? = bookListResultState.value

    when (bookListResult) {
        is ResultStatus.Loading -> ShowProgress()
        is ResultStatus.Success -> {
            BookListScreen(
                bookList = (bookListResult as ResultStatus.Success<BookList>).data,
                onNavigateOnDashboardCTA)
        }
        is ResultStatus.Failure -> ShowError((bookListResult as ResultStatus.Failure<List<BookItem>>).exception)
    }
}

@Composable
fun BookListScreen(bookList: BookList, onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = bookList.items as List<BookItem>,
            itemContent = {
                BookListCardItem(book = it, onNavigateOnDashboardCTA)
            })
    }
}

@Composable
fun BookListCardItem(book: BookItem, onNavigateOnDashboardCTA: (Dashboardctatypes) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onNavigateOnDashboardCTA(Dashboardctatypes.navigateToDetails(book))
            },
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        BookListRowItem(book)
    }
}

@Composable
fun BookListRowItem(
    bookItem: BookItem
) {
    Row {
        //TODO change drawable icon

        bookItem.volumeInfo?.imageLinks?.smallThumbnail?.let { imageUrl ->
            val image = loadImage(url = imageUrl, defaultImage = DEFAULT_IMAGE).value
            image?.let { img->
                Image(bitmap = img.asImageBitmap(), contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(width = 24.dp, height = 36.dp)
                        //.clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                )
            }
        }
        Column (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(text = bookItem.volumeInfo?.title?:"", style = MaterialTheme.typography.h6, fontSize = 11.sp)
            Text(text = bookItem.volumeInfo?.authors?.get(0)?:"", style = MaterialTheme.typography.caption, fontSize=10.sp)
            Text(text = getPrice(bookItem.saleInfo?.listPrice?.currencyCode,
                bookItem.saleInfo?.listPrice?.amount.toString() ?: ""
            ), style = MaterialTheme.typography.body2, fontSize=12.sp)
        }
    }
}