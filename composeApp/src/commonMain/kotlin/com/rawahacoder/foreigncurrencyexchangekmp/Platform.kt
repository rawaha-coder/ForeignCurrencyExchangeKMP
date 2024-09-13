package com.rawahacoder.foreigncurrencyexchangekmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform