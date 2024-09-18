package com.rawahacoder.foreigncurrencyexchangekmp

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android Platform"
}

actual fun getPlatform(): Platform = AndroidPlatform()