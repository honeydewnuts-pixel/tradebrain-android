package com.tradebrain.app

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.IBinder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ScreenshotService : Service() {
    private val timer = Timer()
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer.scheduleAtFixedRate(object: TimerTask(){
            override fun run() {
                val bitmap = takeScreenshot()
                sendToBrain(bitmap)
            }
        }, 0, 60000)
        return START_STICKY
    }
    
    private fun sendToBrain(bitmap: Bitmap){
        val file = File(cacheDir, "chart.jpg")
        FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it) }
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        val body = MultipartBody.Part.createFormData("file", "chart.jpg", requestFile)
        
        ApiClient.brainApi.analyze(body).enqueue(object: retrofit2.Callback<SignalResponse>{
            override fun onResponse(call: retrofit2.Call<SignalResponse>, response: retrofit2.Response<SignalResponse>) {
                val signal = response.body()?.signal
                if(signal != "WAIT") TradeExecutor.executeTrade(signal!!)
            }
            override fun onFailure(call: retrofit2.Call<SignalResponse>, t: Throwable) {}
        })
    }
    
    private fun takeScreenshot(): Bitmap {
        // PLACEHOLDER: Creates a test image with timestamp
        val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLUE)
        val paint = Paint().apply { color = Color.WHITE; textSize = 30f }
        canvas.drawText("Test ${System.currentTimeMillis()}", 50f, 200f, paint)
        return bitmap
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
}
