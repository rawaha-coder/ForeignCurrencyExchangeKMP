package com.rawahacoder.foreigncurrencyexchangekmp.domain

import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState
import kotlinx.coroutines.flow.Flow

interface MongoDBRepo {

    fun settingRealm()

    suspend fun addCurrencyAmountData(currencyObject: CurrencyObject)

    fun retrieveCurrencyAmountData(): Flow<RequestState<List<CurrencyObject>>>

    suspend fun dbCleanUp()
}