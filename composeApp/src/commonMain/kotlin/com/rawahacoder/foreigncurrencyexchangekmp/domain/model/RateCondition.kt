package com.rawahacoder.foreigncurrencyexchangekmp.domain.model

import androidx.compose.ui.graphics.Color
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.staleColor
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.freshColor


enum class RateCondition(val titleRate: String, val colorRate: Color) {

    IdleCondition(
        titleRate = "-----",
        colorRate = Color.White
    ),

    FreshCondition(
        titleRate = "New Rates",
        colorRate = freshColor
    ),

    StaleCondition(
        titleRate = "Rate are not new",
        colorRate = staleColor
    )
}