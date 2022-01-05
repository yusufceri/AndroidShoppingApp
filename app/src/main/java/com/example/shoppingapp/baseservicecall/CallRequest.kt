package com.example.shoppingapp.baseservicecall

class CallRequest constructor(
    val identifier: String,
    val taskType: TaskType
) {

    enum class TaskType {
        DATA,
        NETWORK
    }
}