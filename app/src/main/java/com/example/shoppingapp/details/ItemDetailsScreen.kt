package com.example.shoppingapp.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.details.detailsctatypes.DetailsCTATypes
import com.example.shoppingapp.utils.ShowError
import com.example.shoppingapp.utils.ShowProgress
import com.example.shoppingapp.vms.ResultStatus


@Composable
fun ScreenWithTopBar(
    id: Int,
    viewModel: DetailsViewModel,
    onNavigateOnDetailsCTA: (DetailsCTATypes) -> Unit
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
                        text = context.getString(R.string.title_activity_details),
                        // below line is use
                        // to give text color.
                        color = Color.White
                    )
                },
                navigationIcon = {
                    // navigation icon is use
                    // for drawer icon.
                    IconButton(onClick = {
                        (context as DetailsActivity).onBackPressed()
                    }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                actions = {
                    //Navigate to cart
                    IconButton(onClick = {
                        onNavigateOnDetailsCTA(DetailsCTATypes.navigateToCart())
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
            BookDetailsContent(id, viewModel = viewModel, onNavigateOnDetailsCTA)
        })
}

@Composable
fun BookDetailsContent(
    id: Int,
    viewModel: DetailsViewModel,
    onNavigateOnDetailsCTA: (DetailsCTATypes) -> Unit
) {
    val bookResultState: State<ResultStatus<Any?>?> = viewModel.getBookDetails.observeAsState<ResultStatus<Any?>>()
    val bookResult: ResultStatus<Any?>? = bookResultState.value

    when (bookResult) {
        is ResultStatus.Loading -> ShowProgress()
        is ResultStatus.Success -> {
            DetailsScreen(
                book = (bookResult as ResultStatus.Success<Book>).data, viewModel,
                onNavigateOnDetailsCTA)
        }
        is ResultStatus.Failure -> ShowError((bookResult as ResultStatus.Failure<Book>).exception)
    }
}

@Composable
fun DetailsScreen(book: Book, viewModel: DetailsViewModel, onNavigateOnDetailsCTA: (DetailsCTATypes) -> Unit) {
    Scaffold(
        content = {
            DetailsScreenContent(book,viewModel,onNavigateOnDetailsCTA)
        }
    )
}

@Composable
fun DetailsScreenContent(book: Book, viewModel: DetailsViewModel, onNavigateOnDetailsCTA: (DetailsCTATypes) -> Unit) {
    val srollState = rememberScrollState()

    val isItemAddedState: State<ResultStatus<Any?>?> = viewModel.addBookToCartLiveData.observeAsState(ResultStatus.Success<Boolean>(false))
    val isItemAdded = (isItemAddedState.value as ResultStatus.Success<Boolean>)?.data //?: false

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(srollState)
                ) {
                    Title(book)
                    ProfileHeader(
                        book,
                        this@BoxWithConstraints.maxHeight
                    )

                    if(isItemAdded) {
                        GenericButton(
                            text = stringResource(R.string.add_to_cart),
                            DetailsCTATypes.addToCart(book),
                            onNavigateOnDetailsCTA,
                            isEnabled = false
                        )
                    } else {
                        GenericButton(
                            text = stringResource(R.string.add_to_cart),
                            DetailsCTATypes.addToCart(book),
                            onNavigateOnDetailsCTA
                        )
                    }
                    GenericButton(text = stringResource(R.string.buy_now), DetailsCTATypes.buyNow(book), onNavigateOnDetailsCTA)
                    ProfileContent(book = book, containerHeight = this@BoxWithConstraints.maxHeight)
                }
            }
        }
    }
}

@Composable
fun GenericButton(
    text: String,
    detailsCTATypes: DetailsCTATypes,
    onNavigateOnDetailsCTA: (DetailsCTATypes) -> Unit,
    isEnabled: Boolean = true
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Button(
            onClick = {
                onNavigateOnDetailsCTA(detailsCTATypes)
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White
            ),
            enabled = isEnabled
        ) {
            Text(text, color = Color.Black)
        }
    }
}

@Composable
fun ProfileHeader(
    book: Book,
    containerHeight: Dp
) {
    Image(
        painter = painterResource(id = R.drawable.abc_vector_test),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .heightIn(max = containerHeight / 3)
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

@Composable
fun Title(book: Book) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)) {
        Text(
            text = book.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfileProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Visible
        )
    }
}


@Composable
fun ProfileContent(book: Book, containerHeight: Dp) {
    Column {
        ProfileProperty(stringResource(R.string.book_price), book.price.toString())
        ProfileProperty(stringResource(R.string.book_description), book.description ?: "")
    }
}