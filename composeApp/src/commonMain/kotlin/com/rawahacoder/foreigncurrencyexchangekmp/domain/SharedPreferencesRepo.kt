package com.rawahacoder.foreigncurrencyexchangekmp.domain


interface SharedPreferencesRepo {
    suspend fun storedWhenLastUpdated(lastUpdate: String)
    suspend fun isFetchedDataFresh(currentTimeStamp: Long) : Boolean
}