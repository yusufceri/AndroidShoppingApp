package com.example.shoppingapp.details

import androidx.lifecycle.*
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.vms.ResultStatus

class DetailsViewModel(private val repo: Repo): ViewModel() {
    val getBookDetails: LiveData<ResultStatus<Any?>>
        get() = _getBookDetails

   private lateinit var _getBookDetails: MutableLiveData<ResultStatus<Any?>>

   private var _addBookToCartLiveData = MutableLiveData<Book>()
   val addBookToCartLiveData = _addBookToCartLiveData.switchMap {
       liveData {
           emit(ResultStatus.Loading<List<Book>>())
           try {
               //emit(repo.addToCart(book))
               emit(repo.addToCart(it))
           } catch (e: Exception) {
               emit(ResultStatus.Failure<Exception>(e))
           }
       }
   }
//        get() = _addBookToCartLiveData ?: MutableLiveData<ResultStatus<Any?>>()



//    = liveData(Dispatchers.IO) {
//        emit(ResultStatus.Success<List<Book>>(listOf()))
//        try {
//            emit(repo.getBookDetails())
//        } catch (e: Exception) {
//            emit(ResultStatus.Failure<Exception>(e))
//        }
//    }

    fun getBookDetails(id: Int): LiveData<ResultStatus<Any?>> {
        _getBookDetails = liveData {
            emit(ResultStatus.Loading<List<Book>>())
            try {
                //emit(repo.getBook(id))
                _getBookDetails.value = repo.getBook(id)
            } catch (e: Exception) {
                emit(ResultStatus.Failure<Exception>(e))
            }
        } as MutableLiveData<ResultStatus<Any?>>
        return getBookDetails
    }

    fun addBookToCart(book: Book) {
        _addBookToCartLiveData.value = book



//        _addBookToCartLiveData = liveData {
//            Log.d("DetailsViewModel","====>>>>> addBookToCart")
//            emit(ResultStatus.Loading<List<Book>>())
//            try {
//                //emit(repo.addToCart(book))
//                _addBookToCartLiveData?.value = repo.addToCart(book)
//            } catch (e: Exception) {
//                emit(ResultStatus.Failure<Exception>(e))
//            }
//        } as MutableLiveData<ResultStatus<Any?>>
    }

}

class DetailsViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}



//class DetailsViewModel(repo: Repo): ViewModel() {
////    private var _todoItems = MutableLiveData(listOf<Book>())
////    val todoItems: LiveData<Any> = fetchAllBooks
//
//    var fetchAllBooks: LiveData<ResultStatus<Any>> = liveData(Dispatchers.IO) {
//        emit(ResultStatus.Success<List<Book>>(listOf()))
//        try {
//            emit(repo.getAllBooks())
//        } catch (e: Exception) {
//            emit(ResultStatus.Failure<Exception>(e))
//        }
//    }
//
////    fun getFetchAllBooks(): LiveData<Any> {
////        return fetchAllBooks
////    }
//
//        fun getBookDetails(id: Int) {
//        getBookDetails = liveData(Dispatchers.IO) {
//            emit(ResultStatus.Success<List<Book>>(listOf()))
//            try {
//                emit(repo.getBook(id))
//            } catch (e: Exception) {
//                emit(ResultStatus.Failure<Exception>(e))
//            }
//        }
//    }
//
//
//}
//
//class DetailsViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
//    }
//}