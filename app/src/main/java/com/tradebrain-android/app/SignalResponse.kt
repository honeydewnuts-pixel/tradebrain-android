package com.tradebrain.app

data class SignalResponse(
    val signal: String,
    val confidence: Int,
    val reasons: List<String>
)
