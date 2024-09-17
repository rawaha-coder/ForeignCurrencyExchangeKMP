package com.rawahacoder.foreigncurrencyexchangekmp


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import com.rawahacoder.foreigncurrencyexchangekmp.di.initKoin
import com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.screen.HomePage
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {

    initKoin()

    MaterialTheme{
        Navigator(HomePage())
    }

}