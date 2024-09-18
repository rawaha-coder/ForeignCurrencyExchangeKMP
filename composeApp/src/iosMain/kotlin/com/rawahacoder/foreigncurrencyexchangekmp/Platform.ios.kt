package com.rawahacoder.foreigncurrencyexchangekmp


class IOSPlatform: Platform {
    override val name: String = "iOS Platform"
}

actual fun getPlatform(): Platform = IOSPlatform()