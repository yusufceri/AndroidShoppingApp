package com.example.shoppingapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.shoppingapp.data.model.LoginResponse
import com.example.shoppingapp.domain.Repo
import com.example.shoppingapp.home.SPLASH_SCREEN_DURATION
import com.example.shoppingapp.vms.ResultStatus
import kotlinx.coroutines.delay

class LoginViewModel(private val repo: Repo): ViewModel() {
    val sendCredentials: LiveData<ResultStatus<Any?>>
        get() = _sendCredentials

    val splashDuration: LiveData<Boolean>
        get() = _splashDuration
    private var _splashDuration = MutableLiveData<Boolean>()

    private lateinit var _sendCredentials: MutableLiveData<ResultStatus<Any?>>

    fun login(email: String, password: String): LiveData<ResultStatus<Any?>> {
        _sendCredentials = liveData {
            emit(ResultStatus.Loading<LoginResponse>())
            try {
                _sendCredentials.value = repo.loginApp(email, password)
            } catch (e: Exception) {
                emit(ResultStatus.Failure<Exception>(e))
            }
        } as MutableLiveData<ResultStatus<Any?>>
        return sendCredentials
    }

    fun setSplashScreen(): LiveData<Boolean> {
        _splashDuration = liveData<Boolean> {
            delay(SPLASH_SCREEN_DURATION)
            _splashDuration.value = true
        } as MutableLiveData<Boolean>
        return splashDuration
    }
}