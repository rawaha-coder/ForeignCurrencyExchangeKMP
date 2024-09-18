package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component.HomePageHeader
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.surfaceColor

class HomePage: Screen {
    @Composable
    override fun Content() {

        val screenModel = getScreenModel<HomePageViewModel>()
        val rateCondition by screenModel.rateRefreshStatus
        val currencyFrom by screenModel.sourceCurrency
        val currencyTo by screenModel.targetCurrency
        var amountDigits by rememberSaveable { mutableStateOf(0.0)}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
        ) {
            HomePageHeader(
                status = rateCondition,
                source = currencyFrom,
                target = currencyTo,
                amountNumber = amountDigits,
                onAmountNumberChange = {
                    amountDigits = it
                },
                onSwitchClick = {

                },
                onRatesRefresh = {
                    screenModel.passEvent(HomePageUIEvent.RefreshRatesEvent)
                }
            )
        }
    }
}