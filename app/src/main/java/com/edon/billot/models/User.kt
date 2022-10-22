package com.edon.billot.models

data class User(
    var id: Int = 0,
    var userName: String = "Username",
    var points: Int = 0,
    var bill: Float = 0.0f
)
