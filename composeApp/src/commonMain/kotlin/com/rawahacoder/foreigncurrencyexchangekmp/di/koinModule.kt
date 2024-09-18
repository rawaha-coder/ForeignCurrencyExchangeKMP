package com.rawahacoder.foreigncurrencyexchangekmp.di

import com.rawahacoder.foreigncurrencyexchangekmp.data.local.MongoDBRepoImpl
import com.rawahacoder.foreigncurrencyexchangekmp.data.local.SharedPreferencesImpl
import com.rawahacoder.foreigncurrencyexchangekmp.data.remote.thirdpartyapi.ForeignCurrencyExchangeApiServiceImpl
import com.rawahacoder.foreigncurrencyexchangekmp.domain.ForeignCurrencyExchangeApiService
import com.rawahacoder.foreigncurrencyexchangekmp.domain.MongoDBRepo
import com.rawahacoder.foreigncurrencyexchangekmp.domain.SharedPreferencesRepo
import com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen.HomePageViewModel
import org.koin.dsl.module
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind


fun initKoin(){
    startKoin{
        modules(moduleApplication)
    }
}

val moduleApplication = module {

    single { Settings() }

    single<SharedPreferencesRepo> { SharedPreferencesImpl(settings = get()) }

    single<MongoDBRepo> { MongoDBRepoImpl() }

    single<ForeignCurrencyExchangeApiService> { ForeignCurrencyExchangeApiServiceImpl(preferencesImpl = get()) }

    factory {
        HomePageViewModel(
            preferences = get(),
            apiService = get(),
            mongoDBRepo = get()
        )
    }
}