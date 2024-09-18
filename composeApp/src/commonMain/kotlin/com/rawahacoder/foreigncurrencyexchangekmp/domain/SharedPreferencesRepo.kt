package com.rawahacoder.foreigncurrencyexchangekmp.domain

import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyKey
import kotlinx.coroutines.flow.Flow


interface SharedPreferencesRepo {
    suspend fun storedWhenLastUpdated(lastUpdate: String)
    suspend fun isFetchedDataFresh(currentTimeStamp: Long) : Boolean
    suspend fun storeFromCurrencyKey(code: String)
    suspend fun storeToCurrencyKey(code: String)
    fun retrieveFromCurrencyKey(): Flow<CurrencyKey>
    fun retrieveToCurrencyKey(): Flow<CurrencyKey>
}