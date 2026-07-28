package com.tradebrain.app

object DeviceLock {
    fun checkDevice(account: String, deviceId: String, server: String, callback: (Boolean) -> Unit){
        // API call: POST /check-device
        // Server returns true or false
        callback(true) // For now allow. We connect this after cloud is live
    }
}
