package com.example.shoppingapp.details

import androidx.lifecycle.*
import com.example.shoppingapp.data.model.BookItem
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.vms.ResultStatus

class DetailsViewModel(private val repo: Repo): ViewModel() {
    val getBookDetails: LiveData<ResultStatus<Any?>>
        get() = _getBookDetails

   private lateinit var _getBookDetails: MutableLiveData<ResultStatus<Any?>>

   private var _addBookToCartLiveData = MutableLiveData<BookItem>()
   val addBookToCartLiveData = _addBookToCartLiveData.switchMap {
       liveData {
           try {
               emit(repo.addToCart(it))
           } catch (e: Exception) {
               emit(ResultStatus.Failure<Exception>(e))
           }
       }
   }

    fun getBookDetails(id: String): LiveData<ResultStatus<Any?>> {
        _getBookDetails = liveData {
            emit(ResultStatus.Loading<BookItem>())
            try {
                _getBookDetails.value = repo.getBook(id)
            } catch (e: Exception) {
                emit(ResultStatus.Failure<Exception>(e))
            }
        } as MutableLiveData<ResultStatus<Any?>>
        return getBookDetails
    }

    fun addBookToCart(book: BookItem) {
        _addBookToCartLiveData.value = book
    }

}

class DetailsViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}