package com.example.rts_fragment.RetrofitData

import com.example.rts_fragment.GyeonguiApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GyeonguiObject {
    private const val baseUrl = "http://swopenapi.seoul.go.kr/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //getApi는 Gyeongui_Api객체이다.
    val getApi : GyeonguiApi = retrofit.create(GyeonguiApi::class.java)

}