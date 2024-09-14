package com.rawahacoder.foreigncurrencyexchangekmp.domain.model



data class ApiResponse(
    val meta: MedaDataFromApi,
    val data: Map<String, CurrencyObject>
)

data class MedaDataFromApi(
    val last_updated_at: String
)

data class CurrencyObject(
    val code: String,
    val value: Double
)