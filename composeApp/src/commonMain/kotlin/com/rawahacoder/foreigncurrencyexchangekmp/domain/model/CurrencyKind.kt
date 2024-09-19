package com.rawahacoder.foreigncurrencyexchangekmp.domain.model

sealed class CurrencyKind(val key: CurrencyKey) {
    data class FromSource(val currencyCode: CurrencyKey) : CurrencyKind(currencyCode)
    data class ToTarget(val currencyCode: CurrencyKey) : CurrencyKind(currencyCode)
    data object None: CurrencyKind(CurrencyKey.GBP)
}