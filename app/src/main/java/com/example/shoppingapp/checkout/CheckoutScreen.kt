package com.example.shoppingapp.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.checkout.checkoutctatypes.CheckoutCTATypes
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.CheckoutData
import com.example.shoppingapp.shoppingcart.CartCTATypes
import com.example.shoppingapp.utils.ShowError
import com.example.shoppingapp.utils.ShowProgress
import com.example.shoppingapp.vms.ResultStatus

@Composable
fun ScreenWithTopBar(
    viewModel: CheckoutViewModel,
    onNavigateOnCheckoutCTA: (CheckoutCTATypes) -> Unit
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
                        text = context.getString(R.string.title_activity_checkout),
                        // below line is use
                        // to give text color.
                        color = Color.White
                    )
                },
                navigationIcon = {
                    // navigation icon is use
                    // for drawer icon.
                    IconButton(onClick = {
                        (context as CheckoutActivity).onBackPressed()
                    }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(Icons.Filled.ArrowBack, "")
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
            val confirmCheckoutState: State<ResultStatus<Any?>?> = viewModel.confirmCheckoutLiveData.observeAsState(null)
            (confirmCheckoutState.value)?.let { checkoutResultStatus ->
                when (checkoutResultStatus) {
                    is ResultStatus.Loading -> ShowProgress()
                    is ResultStatus.Success -> {
                        confirmationDone(onNavigateOnCheckoutCTA)
                    }
                    is ResultStatus.Failure -> ShowError((checkoutResultStatus as ResultStatus.Failure<Book>).exception)
                }
            } ?: run {
                CheckoutContent(viewModel = viewModel, onNavigateOnCheckoutCTA)
            }
        })
}

@Composable
fun confirmationDone(
    onNavigateOnCheckoutCTA: (CheckoutCTATypes) -> Unit
) {
    Row {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
                top = 48.dp
            ).fillMaxWidth().border(
                width = 0.05.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.confirm_order),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = stringResource(id = R.string.return_to_dashboard),
                style = MaterialTheme.typography.body1,
                fontSize = 10.sp,
                color = Color.Red,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 18.dp, bottom = 12.dp).clickable {
                    onNavigateOnCheckoutCTA(CheckoutCTATypes.navigateToDashboard())
                }
            )
        }
    }
}

@Composable
fun CheckoutContent(
    viewModel: CheckoutViewModel,
    onNavigateOnCheckoutCTA: (CheckoutCTATypes) -> Unit
) {
    val checkoutResultState: State<ResultStatus<Any>?> = viewModel.getCheckoutData.observeAsState<ResultStatus<Any>>()
    val checkoutResult: ResultStatus<Any>? = checkoutResultState.value

    when (checkoutResult) {
        is ResultStatus.Loading -> ShowProgress()
        is ResultStatus.Success -> {
            CheckoutScreen(
                checkoutData = (checkoutResult as ResultStatus.Success<CheckoutData>).data, viewModel,
                onNavigateOnCheckoutCTA)
        }
        is ResultStatus.Failure -> ShowError((checkoutResult as ResultStatus.Failure<Book>).exception)
    }
}

@Composable
fun CheckoutScreen(checkoutData: CheckoutData, viewModel: CheckoutViewModel, onNavigateOnCheckoutCTA: (CheckoutCTATypes) -> Unit) {
    Scaffold(
        content = {
            CheckoutScreenContent(checkoutData,viewModel,onNavigateOnCheckoutCTA)
        }
    )
}

@Composable
fun CheckoutScreenContent(checkoutData: CheckoutData, viewModel: CheckoutViewModel, onNavigateOnCheckoutCTA: (CheckoutCTATypes) -> Unit) {
    Column(modifier = Modifier
        .padding(16.dp)
        .border(0.5.dp, Color.Gray, RectangleShape)) {
        Row {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    top = 16.dp
                )
            ) {
                Text(
                    text = stringResource(id = R.string.shippingto, checkoutData.shippingAddress),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Row {
            Divider(
                modifier = Modifier
                    .offset(y = 4.dp)
                    .padding(start = 4.dp, end = 4.dp),
                color = Color.LightGray,
                thickness = 0.5.dp
            )
        }
        Row(modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.items),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.shipping),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.estimated_tax),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.order_total),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(end = 4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = checkoutData.itemList?.let { itemList ->
                        itemList.sumOf {it.saleInfo?.listPrice?.amount ?: 0.0}.toString()
                    } ?: run {
                             "0"
                    },
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = checkoutData.shippingFee.toString(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = checkoutData.estimatedTax.toString(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = checkoutData.orderTotal.toString(),
                    color = colorResource(id = R.color.red),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row(modifier = Modifier
            .padding(top = 24.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .border(0.2.dp, Color.LightGray, RectangleShape)) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.payment_method),
                    style = MaterialTheme.typography.body1,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Column(horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(end = 4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
                    text = "Visa Ending 1222",
                    style = MaterialTheme.typography.body1,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            }

        }
        Row(modifier = Modifier
            .padding(top = 24.dp, bottom = 12.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Yellow)
                    .border(
                        width = 0.5.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(5.dp)
                    ),
                onClick = {
                    onNavigateOnCheckoutCTA(CheckoutCTATypes.placeOrder())
                },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(stringResource(id = R.string.confirm_order), color = Color.Black)
            }
        }
    }
}