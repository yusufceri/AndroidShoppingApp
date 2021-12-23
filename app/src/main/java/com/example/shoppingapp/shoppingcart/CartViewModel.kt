package com.example.shoppingapp.shoppingcart

import androidx.lifecycle.*
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.vms.ResultStatus

class CartViewModel(private val repo: Repo) : ViewModel() {
    private var _getCartContentLiveData = MutableLiveData<Book>()
    val getCartContentLiveData: LiveData<ResultStatus<Any>> = liveData {
            emit(ResultStatus.Loading<List<Book>>())
            try {
                emit(repo.getCart())
            } catch (e: Exception) {
                emit(ResultStatus.Failure<Exception>(e))
            }
    }
}

class CartViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}