package com.example.urrencytrans.retrofit

import com.example.urrencytrans.model.Daily
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ValuteApi {
    @GET("/daily_json.js")
    @Headers("Content-Type: application/json")
    fun valuteItem(): Observable<Daily>

}