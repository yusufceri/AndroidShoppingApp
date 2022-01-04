package com.example.shoppingapp.home

import androidx.lifecycle.*
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.vms.ResultStatus
import kotlinx.coroutines.*

const val SPLASH_SCREEN_DURATION = 1000L

class DashboardViewModel(repo: Repo): ViewModel() {
    val splashDuration: LiveData<Boolean>
        get() = _splashDuration
    private var _splashDuration = MutableLiveData<Boolean>()

    var fetchAllBooks: LiveData<ResultStatus<Any>> = liveData(Dispatchers.IO) {
        try {
            emit(repo.getAllBooks())
        } catch (e: Exception) {
            emit(ResultStatus.Failure<Exception>(e))
        }
    }

    fun setSplashScreen(): LiveData<Boolean> {
        _splashDuration = liveData<Boolean> {
            delay(SPLASH_SCREEN_DURATION)
            _splashDuration.value = true
        } as MutableLiveData<Boolean>
        return splashDuration
    }

}

class DashboardViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}