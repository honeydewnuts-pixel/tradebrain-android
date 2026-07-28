package com.tradebrain.app

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface BrainApi {
    @Multipart
    @POST("/analyze")
    fun analyze(@Part image: MultipartBody.Part): Call<SignalResponse>
}

object ApiClient {
    private const val BASE_URL = "https://your-brain.onrender.com/" // CHANGE TO YOUR REAL URL LATER
    
    val brainApi: BrainApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BrainApi::class.java)
}
