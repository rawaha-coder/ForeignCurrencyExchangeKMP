package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    messageToShow: String? = null
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = messageToShow ?: "Nothing found.",
            textAlign = TextAlign.Center
        )

    }

}