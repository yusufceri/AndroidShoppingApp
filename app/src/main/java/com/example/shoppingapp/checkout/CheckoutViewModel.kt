package com.example.shoppingapp.checkout

import androidx.lifecycle.*
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.vms.ResultStatus
import kotlinx.coroutines.Dispatchers

class CheckoutViewModel(private val repo: Repo): ViewModel() {
    var getCheckoutData: LiveData<ResultStatus<Any>> = liveData(Dispatchers.IO) {
        emit(ResultStatus.Loading<List<Book>>())
        try {
            emit(repo.getCheckoutConfirmationData())
        } catch (e: Exception) {
            emit(ResultStatus.Failure<Exception>(e))
        }
    }

    private var _confirmCheckoutLiveData = MutableLiveData<Boolean>()
    val confirmCheckoutLiveData = _confirmCheckoutLiveData.switchMap {
        liveData {
            emit(ResultStatus.Loading<List<Book>>())
            try {
                emit(repo.orderConfirmed())
            } catch (e: Exception) {
                emit(ResultStatus.Failure<Exception>(e))
            }
        }
    }

    fun orderConfirmed() {
        _confirmCheckoutLiveData.value = true
    }
}

class CheckoutViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}