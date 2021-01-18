package com.caroline.taipeizoo.network

import com.caroline.taipeizoo.model.InfoResult
import com.caroline.taipeizoo.model.TopResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


/**
 * Created by nini on 2020/11/25.
 */

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://data.taipei/api/v1/dataset/")
    .client(OkHttpClient())
    .build()

interface APIService {

    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    suspend fun getIntroduction(): TopResult


}

object ZooApi {
    val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}