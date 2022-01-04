package com.example.shoppingapp.shoppingcart

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.app.SPApplication
import com.example.shoppingapp.checkout.CheckoutActivity
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.BookItem
import com.example.shoppingapp.home.BookListCardItem
import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes
import com.example.shoppingapp.utils.*
import com.example.shoppingapp.vms.ResultStatus

@Composable
fun CartContentWithTopBar(viewModel: CartViewModel, onNavigateCartCTA: (CartCTATypes) -> Unit) {
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
                        text = context.getString(R.string.title_activity_cart),
                        // below line is use
                        // to give text color.
                        color = Color.White
                    )
                },
                navigationIcon = {
                    // navigation icon is use
                    // for drawer icon.
                    IconButton(onClick = {
                        (context as CartScreenActivity).onBackPressed()
                    }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                actions = {
                    //Navigate to cart
                    IconButton(onClick = {
                        onNavigateCartCTA(CartCTATypes.navigateToCart())
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
            CartContent(viewModel = viewModel, onNavigateCartCTA)
        })
}

@Composable
fun CartContent(
    viewModel: CartViewModel,
    onNavigateCartCTA: (CartCTATypes) -> Unit
) {
    val cartResultState: State<ResultStatus<Any>?> = viewModel.getCartContentLiveData.observeAsState<ResultStatus<Any>>()
    val cartResult: ResultStatus<Any>? = cartResultState.value

    when (cartResult) {
        is ResultStatus.Loading -> ShowProgress()
        is ResultStatus.Success -> {
           if((cartResult as ResultStatus.Success<List<BookItem>>).data != null &&
               (cartResult as ResultStatus.Success<List<BookItem>>).data.size > 0) {
               cartScreen(
                   bookList = (cartResult as ResultStatus.Success<List<BookItem>>).data,
                   onNavigateCartCTA = onNavigateCartCTA
               )
           } else {
               emptyCartScreen(onNavigateCartCTA)
           }
        }
        is ResultStatus.Failure -> ShowError((cartResult as ResultStatus.Failure<List<Book>>).exception)
    }
}

@Composable
fun emptyCartScreen(
    onNavigateCartCTA: (CartCTATypes) -> Unit
) {
    Row(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 36.dp, bottom = 24.dp),
                text = stringResource(id = R.string.cart_empty),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.LightGray),
                onClick = {
                    onNavigateCartCTA(CartCTATypes.navigatetoDashboard())
                },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(stringResource(id = R.string.return_to_dashboard), color = Color.Black)
            }
        }
    }
}

@Composable
fun cartScreen(
    bookList: List<BookItem>,
    onNavigateCartCTA: (CartCTATypes) -> Unit
) {
    Row {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically),
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 0.5.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    onClick = {
                        onNavigateCartCTA(CartCTATypes.ProceedToCheckout())
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.Yellow
                    )
                ) {
                    Text(stringResource(id = R.string.proceed_to_checkout), color = Color.Black)
                }

                    LazyColumn(
                        modifier = Modifier.offset(y = 8.dp)
                    ) {
                        items(
                            items = bookList,
                            itemContent = {
                                CartListCardItem(book = it, onNavigateCartCTA)
                            })
                    }
            }
    }
}

@Composable
fun CartListCardItem(
    book: BookItem,
    onNavigateCartCTA: (CartCTATypes) -> Unit
) {
    Card(
        modifier = Modifier
            .offset(y = 10.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                /** TODO */
            },
        elevation = 0.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        BookListRowItem(book)
    }

    Divider(
        modifier = Modifier.offset(y = 4.dp),
        color =Color.LightGray,
        thickness = 0.5.dp
    )
}

@Composable
fun BookListRowItem(
    bookItem: BookItem
) {
    Row {
        //TODO change drawable icon
        Column (
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically),
        ) {
            bookItem.volumeInfo?.imageLinks?.smallThumbnail?.let { imageUrl ->
                val image = loadImage(url = imageUrl, defaultImage = DEFAULT_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 24.dp, height = 36.dp)
                            .clip(RoundedCornerShape(corner = CornerSize(3.dp)))
                    )
                }
            }
        }
        Column (
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = bookItem.volumeInfo?.title ?: "",
                style = MaterialTheme.typography.caption,
                fontSize = 11.sp
            )
            Text(
                text = bookItem.volumeInfo?.authors?.get(0) ?: "",
                style = MaterialTheme.typography.caption,
                fontSize = 10.sp
            )
            Text(
                modifier = Modifier.offset(y = 3.dp),
                text = getPrice(bookItem.saleInfo?.listPrice?.currencyCode, bookItem.saleInfo?.listPrice?.amount.toString()),
                style = MaterialTheme.typography.body2,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}