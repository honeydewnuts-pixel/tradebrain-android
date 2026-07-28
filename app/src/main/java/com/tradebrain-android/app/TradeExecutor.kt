package com.tradebrain.app

object TradeExecutor {
    fun executeTrade(signal: String) {
        android.util.Log.d("TradeBrain", "Trade signal received: $signal at ${System.currentTimeMillis()}")
    }
}
