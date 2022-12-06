package com.example.rts_fragment

import retrofit2.Call
import retrofit2.http.GET

interface GyeonguiApi{
    @GET("api/subway/425171754a77657335354b726d7069/json/realtimeStationArrival/0/10/화전")
    fun changeEnd(): Call<Gyeongui>

}