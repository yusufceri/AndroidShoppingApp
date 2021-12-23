package com.example.shoppingapp.home

import androidx.lifecycle.*
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.vms.ResultStatus
import kotlinx.coroutines.Dispatchers

class HomeViewModel(repo: Repo): ViewModel() {
//    private var _todoItems = MutableLiveData(listOf<Book>())
//    val todoItems: LiveData<Any> = fetchAllBooks

    var fetchAllBooks: LiveData<ResultStatus<Any>> = liveData(Dispatchers.IO) {
        emit(ResultStatus.Loading<List<Book>>())
        try {
            emit(repo.getAllBooks())
        } catch (e: Exception) {
            emit(ResultStatus.Failure<Exception>(e))
        }
    }

//    fun getFetchAllBooks(): LiveData<Any> {
//        return fetchAllBooks
//    }

}

class HomeViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}