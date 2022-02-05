package com.example.urrencytrans.retrofit

object Common {
    private val BASE_URL = "https://www.cbr-xml-daily.ru/"
    val valuteApi: ValuteApi
        get() = RetrofitClient.getClient(BASE_URL).create(ValuteApi::class.java)
}