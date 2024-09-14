package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import com.rawahacoder.foreigncurrencyexchangekmp.data.remote.thirdpartyapi.ForeignCurrencyExchangeApiServiceImpl

class HomePage: Screen {
    @Composable
    override fun Content() {
        LaunchedEffect(Unit){
            ForeignCurrencyExchangeApiServiceImpl().retrieveLatestForeignCurrencyExchangeRate()
        }
    }
}