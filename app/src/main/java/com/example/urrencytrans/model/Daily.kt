package com.example.urrencytrans.model

class Daily(
    var Valute: HashMap<String, ValuteItem>
)

class ValuteItem(
    var CharCode: String,
    var Nominal: Int,
    var Name: String,
    var Value: Double,
    var Previous: Double
)
