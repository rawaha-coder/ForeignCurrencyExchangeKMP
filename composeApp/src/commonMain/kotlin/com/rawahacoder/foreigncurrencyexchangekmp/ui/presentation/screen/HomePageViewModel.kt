package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.rawahacoder.foreigncurrencyexchangekmp.data.local.SharedPreferencesImpl
import com.rawahacoder.foreigncurrencyexchangekmp.domain.ForeignCurrencyExchangeApiService
import com.rawahacoder.foreigncurrencyexchangekmp.domain.SharedPreferencesRepo
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RateCondition
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


sealed class HomePageUIEvent{
    data object RefreshRatesEvent : HomePageUIEvent()
}

class HomePageViewModel(
    private val preferences: SharedPreferencesRepo,
    private val apiService: ForeignCurrencyExchangeApiService
): ScreenModel {
    private var _rateRefreshStatus : MutableState<RateCondition> = mutableStateOf(RateCondition.IdleCondition)
    val rateRefreshStatus: State<RateCondition> = _rateRefreshStatus

    init {
        screenModelScope.launch {
            readNewRates()

        }
    }

    private suspend fun readNewRates(){
        try {
            apiService.retrieveLatestForeignCurrencyExchangeRate()
            readRateCondition()
        }catch (error: Exception){
            println(error.message)
        }
    }

    private suspend fun readRateCondition(){
        _rateRefreshStatus.value = if (preferences.isFetchedDataFresh(currentTimeStamp = Clock.System.now().toEpochMilliseconds()))
            RateCondition.FreshCondition else RateCondition.StaleCondition
    }

    fun passEvent(event: HomePageUIEvent){
        when(event){
            is HomePageUIEvent.RefreshRatesEvent ->{
                screenModelScope.launch {
                    readNewRates()
                }
            }
        }
    }
}