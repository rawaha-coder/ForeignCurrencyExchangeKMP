package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RateCondition
import com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component.HomePageHeader
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.surfaceColor

class HomePage: Screen {
    @Composable
    override fun Content() {

        val screenModel = getScreenModel<HomePageViewModel>()
        val rateCondition by screenModel.rateRefreshStatus
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
        ) {
            HomePageHeader(
                status = rateCondition,
                onRatesRefresh = {
                    screenModel.passEvent(HomePageUIEvent.RefreshRatesEvent)
                }
            )
        }
    }
}