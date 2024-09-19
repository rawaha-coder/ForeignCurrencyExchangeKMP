package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState
import com.rawahacoder.foreigncurrencyexchangekmp.utility.ConvertDouble
import com.rawahacoder.foreigncurrencyexchangekmp.utility.convertUserAmountIntoExchangeAmount
import com.rawahacoder.foreigncurrencyexchangekmp.utility.exchangeRateCalculation

@Composable
fun HomePageBody(
    fromSource: RequestState<CurrencyObject>,
    toTarget: RequestState<CurrencyObject>,
    userInputAmount: Double
){
    var amountExchangeResult by rememberSaveable { mutableStateOf(0.0) }

    val exchangeAmountAnimation by animateValueAsState(
        targetValue = amountExchangeResult,
        animationSpec = tween(durationMillis = 300),
        typeConverter = ConvertDouble()
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
    ){
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${(exchangeAmountAnimation * 100).toLong() / 100}",
                fontSize = 58.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )

            AnimatedVisibility(visible = fromSource.isSuccess() && toTarget.isSuccess()){
                Text(
                    modifier = Modifier.fillMaxWidth().padding(all = 0.dp),
                    text = toTarget.getSuccessData().code,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.5f)
                    else Color.Black.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp)
                .background(
                    color = Color.Green,
                    shape = RoundedCornerShape(100.dp)
                ),
            onClick = {
                if (fromSource.isSuccess() && toTarget.isSuccess()){
                    val exchangeRateResult = exchangeRateCalculation(
                        fromSource = fromSource.getSuccessData().value,
                        toTarget = toTarget.getSuccessData().value
                    )
                    amountExchangeResult = convertUserAmountIntoExchangeAmount(
                        userInputAmount = userInputAmount,
                        exchangeRateAmount = exchangeRateResult
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ){
            Text(
                text = "Show Result",
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

    }


}