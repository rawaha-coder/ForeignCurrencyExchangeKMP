package com.rawahacoder.foreigncurrencyexchangekmp.di

import com.rawahacoder.foreigncurrencyexchangekmp.data.local.SharedPreferencesImpl
import com.rawahacoder.foreigncurrencyexchangekmp.data.remote.thirdpartyapi.ForeignCurrencyExchangeApiServiceImpl
import com.rawahacoder.foreigncurrencyexchangekmp.domain.ForeignCurrencyExchangeApiService
import com.rawahacoder.foreigncurrencyexchangekmp.domain.SharedPreferencesRepo
import org.koin.dsl.module
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin

val moduleApplication = module {
    single { Settings() }
    single<SharedPreferencesRepo> { SharedPreferencesImpl(settings = get()) }
    single<ForeignCurrencyExchangeApiService> { ForeignCurrencyExchangeApiServiceImpl(preferencesImpl = get()) }
}

fun initKoin(){
    startKoin{
        modules(modules = moduleApplication)
    }
}