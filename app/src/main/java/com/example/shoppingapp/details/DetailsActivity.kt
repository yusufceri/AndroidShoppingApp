package com.example.shoppingapp.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.shoppingapp.base.SPABaseActivity
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.details.detailsctatypes.DetailsCTATypes
import com.example.shoppingapp.domain.RepoImpl
import com.example.shoppingapp.home.BookListScreen
import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes
import com.example.shoppingapp.shoppingcart.CartScreenActivity
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.ShowError
import com.example.shoppingapp.utils.ShowProgress
import com.example.shoppingapp.vms.ResultStatus

class DetailsActivity : SPABaseActivity() {
    private val viewModel by viewModels<DetailsViewModel> { DetailsViewModelFactory(RepoImpl(DataSource())) }
//    lateinit var CTANavigation: (type: Int) -> Unit

    private val bookId: Int by lazy {
        intent?.getSerializableExtra(BOOK_DATA_ID) as? Int ?: -1
    }

    private fun navigationCTA(detailsCTAType: DetailsCTATypes) {
        when (detailsCTAType) {
            is DetailsCTATypes.addToCart -> {
//                startActivity(DetailsActivity.newIntent(this, type.book.id))
                //TODO
                // startActivity(DetailsActivity.newIntent(this, ))
                viewModel.addBookToCart(detailsCTAType.book)
//                val addBookToCartStatus: ResultStatus<Any?>? = addBookToCartResult.value
//                when (addBookToCartStatus) {
//                        is ResultStatus.Loading -> ShowProgress()
//                        is ResultStatus.Success -> {
//                            viewModel.addBookToCart((addBookToCartStatus as ResultStatus.Success<Book>).data)
//                        }
//                        is ResultStatus.Failure -> ShowError((addBookToCartStatus as ResultStatus.Failure<List<Book>>).exception)
//                    }

            }
            is DetailsCTATypes.buyNow -> {
                //TODO
//                startActivity(DetailsActivity.newIntent(this, detailsCTAType.book.id))
            }
            is DetailsCTATypes.navigateToCart -> {
                startActivity(CartScreenActivity.newIntent(this))
            }
        }
    }

//    private var onNavigateOnDetailsCTA = { type: DetailsCTATypes ->
//        when(type) {
//            is DetailsCTATypes.addToCart -> {
////                Log.d("Details", "Add To Cart")
////                startActivity(DetailsActivity.newIntent(this, type.book.id))
//                //TODO
//                // startActivity(DetailsActivity.newIntent(this, ))
//                viewModel.addBookToCart(type.book).observe(this) { resultStatus ->
//                    when (resultStatus) {
//                        is ResultStatus.Loading -> ShowProgress()
//                        is ResultStatus.Success -> {
//                            BookListScreen(
//                                bookList = (resultStatus as ResultStatus.Success<List<Book>>).data,
//                                onNavigateOnDashboardCTA)
//                        }
//                        is ResultStatus.Failure -> ShowError((bookListResult as ResultStatus.Failure<List<Book>>).exception)
//                    }
//                }
//            }
//            is DetailsCTATypes.buyNow ->
////                Log.d("Details","Buy Now")
//                //TODO
//                startActivity(DetailsActivity.newIntent(this, type.book.id))
//            is DetailsCTATypes.navigateToCart ->
//                startActivity(CartScreenActivity.newIntent(this))
//        }
//
////        Log.d("MAinScreen", "NAvigateToCart")
////        val book: Book = Book(id = 1, author = "", price = "", title = "")
//        //startActivity(DetailsActivity.newIntent(this, book))
//    }

    companion object {
        private const val BOOK_DATA_ID = "book_data_id"
        fun newIntent(context: Context, bookId: Int) =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(BOOK_DATA_ID, bookId)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getBookDetails(bookId)

        setContent {
            ShoppingAppTheme {
                Surface(color = MaterialTheme.colors.background) {
//                Text(text = "Test ${book.title}")
                    ScreenWithTopBar(bookId, viewModel) {
                        navigationCTA(detailsCTAType = it)
                    } //, onNavigateOnDetailsCTA)
                   // test(bookId, viewModel)
                }
            }
        }
    }
}