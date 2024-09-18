package com.rawahacoder.foreigncurrencyexchangekmp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val meta: MedaDataFromApi,
    val data: Map<String, CurrencyObject>
)

@Serializable
data class MedaDataFromApi(
    val last_updated_at: String
)

@Serializable
data class CurrencyObject(
    val code: String,
    val value: Double
)