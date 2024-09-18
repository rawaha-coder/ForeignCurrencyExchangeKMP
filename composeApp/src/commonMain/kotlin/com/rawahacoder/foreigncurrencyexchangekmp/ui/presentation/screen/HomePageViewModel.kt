package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.rawahacoder.foreigncurrencyexchangekmp.data.local.SharedPreferencesImpl
import com.rawahacoder.foreigncurrencyexchangekmp.domain.ForeignCurrencyExchangeApiService
import com.rawahacoder.foreigncurrencyexchangekmp.domain.MongoDBRepo
import com.rawahacoder.foreigncurrencyexchangekmp.domain.SharedPreferencesRepo
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RateCondition
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


sealed class HomePageUIEvent{
    data object RefreshRatesEvent : HomePageUIEvent()
}

class HomePageViewModel(
    private val preferences: SharedPreferencesRepo,
    private val apiService: ForeignCurrencyExchangeApiService,
    private val mongoDBRepo: MongoDBRepo
): ScreenModel {
    private var _rateRefreshStatus : MutableState<RateCondition> = mutableStateOf(RateCondition.IdleCondition)
    val rateRefreshStatus: State<RateCondition> = _rateRefreshStatus

    private var _currencyFrom: MutableState<RequestState<CurrencyObject>> = mutableStateOf(RequestState.Idle)
    val sourceCurrency: State<RequestState<CurrencyObject>> = _currencyFrom

    private var _currencyTo: MutableState<RequestState<CurrencyObject>> = mutableStateOf(RequestState.Idle)
    val targetCurrency: State<RequestState<CurrencyObject>> = _currencyTo

    private var _overallCurrencies = mutableStateListOf<CurrencyObject>()
    val overallCurrencies : List<CurrencyObject> = _overallCurrencies

    init {
        screenModelScope.launch {
            readNewRates()

        }
    }

    private suspend fun readNewRates(){
        try {
            val localStorage = mongoDBRepo.retrieveCurrencyAmountData().first()
            if (localStorage.isSuccess()){
                if (localStorage.getSuccessData().isNotEmpty()){
                    println("testing= Data is available in MongoDB")
                    _overallCurrencies.addAll(localStorage.getSuccessData())
                    if (!preferences.isFetchedDataFresh(Clock.System.now().toEpochMilliseconds())){
                        println("testing= Data is OLD")
                        getNewDataAndSaveToDatabase()
                    }else{
                        println("testing= Data is FRESH")
                    }
                }else{
                    println("testing= Database is empty")
                    getNewDataAndSaveToDatabase()
                }
            }else if(localStorage.isError()){
                println("testing= Error reading local storage ${localStorage.getErrorMessage()}")
            }

            readRateCondition()

        }catch (error: Exception){
            println(error.message)
        }
    }

    private suspend fun getNewDataAndSaveToDatabase(){
        val readNewData = apiService.retrieveLatestForeignCurrencyExchangeRate()
        if (readNewData.isSuccess()){
            mongoDBRepo.dbCleanUp()
            readNewData.getSuccessData().forEach {
                println("testing= Save ${it.code}")
                mongoDBRepo.addCurrencyAmountData(it)
            }
            println("testing= Update the _overallCurrencies")
            _overallCurrencies.addAll(readNewData.getSuccessData())
        }else if(readNewData.isError()){
            println("testing= Reading failed ${readNewData.getErrorMessage()}")
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