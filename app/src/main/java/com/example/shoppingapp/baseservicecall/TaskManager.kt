package com.example.shoppingapp.baseservicecall

import android.util.Log

object TaskManager {
    suspend fun <T> getTask(callRequest: CallRequest): T? {
        when(callRequest.taskType) {
            CallRequest.TaskType.DATA ->
                return DataManager.fetchData<T>(callRequest.identifier)
            CallRequest.TaskType.NETWORK ->
                Log.d("TaskManager", "Will be implemented")
        }
        return null
    }
}