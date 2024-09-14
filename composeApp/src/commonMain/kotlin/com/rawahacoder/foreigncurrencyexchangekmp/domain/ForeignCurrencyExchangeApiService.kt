package com.rawahacoder.foreigncurrencyexchangekmp.domain

import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState

interface ForeignCurrencyExchangeApiService {
    suspend fun retrieveLatestForeignCurrencyExchangeRate(): RequestState<List<CurrencyObject>>
}