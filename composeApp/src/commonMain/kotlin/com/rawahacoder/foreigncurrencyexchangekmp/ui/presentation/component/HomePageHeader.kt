package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyKey
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyKind
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RateCondition
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.ShowResult
import com.rawahacoder.foreigncurrencyexchangekmp.getPlatform
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.headerColor
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.staleColor
import com.rawahacoder.foreigncurrencyexchangekmp.utility.showCurrentDateTime
import foreigncurrencyexchangekmp.composeapp.generated.resources.Res
import foreigncurrencyexchangekmp.composeapp.generated.resources.exchange
import foreigncurrencyexchangekmp.composeapp.generated.resources.money_exchange
import foreigncurrencyexchangekmp.composeapp.generated.resources.moneyexchange
import org.jetbrains.compose.resources.painterResource



@Composable
fun HomePageHeader(
    status: RateCondition,
    source: RequestState<CurrencyObject>,
    target: RequestState<CurrencyObject>,
    amountNumber: Double,
    onAmountNumberChange: (Double) -> Unit,
    onRatesRefresh: () -> Unit,
    onSwitchClick: () -> Unit,
    onChooseCurrencyKind: (CurrencyKind) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp))
            .background(headerColor)
            .padding(top = if (getPlatform().name == "Android") 0.dp else 26.dp)
            .padding(all = 26.dp)
    ) {

        Spacer(modifier = Modifier.height(26.dp))

        RatesCondition(status = status, onRatesRefresh = onRatesRefresh)

        Spacer(modifier = Modifier.height(26.dp))

        CurrencyInputs(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick,
            onChooseCurrencyKind = onChooseCurrencyKind
        )

        Spacer(modifier = Modifier.height(26.dp))

        InputAmountNumber(
            amountNumber = amountNumber,
            onAmountNumberChange = onAmountNumberChange
        )

    }

}

@Composable
fun RatesCondition(
    status: RateCondition,
    onRatesRefresh: () -> Unit) {

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(Res.drawable.money_exchange),
                contentDescription = "Exchange Rate Illustration"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "Today is",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = Color.White
                )
                Text(
                    text = showCurrentDateTime(),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = Color.White
                )
                Text(
                    text = status.titleRate,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = status.colorRate,
                )
            }
        }

        if (status == RateCondition.StaleCondition){
            IconButton(onClick = onRatesRefresh){
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Refresh icon",
                )
            }
        }

    }

}

@Composable
fun RowScope.ViewCurrency(
    titleHolder: String,
    currencyName: RequestState<CurrencyObject>,
    onClick: () -> Unit
){
    Column(
    modifier = Modifier.weight(1f)
    ){
        Text(
            modifier = Modifier.padding(start = 14.dp),
            text = titleHolder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 10.dp))
                .background(color = Color.White.copy(alpha = 0.05f))
                .height(54.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            currencyName.ShowResult(
                onSuccess = { data ->
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(
                            CurrencyKey.valueOf(
                                data.code
                            ).flagCountry),
                        contentDescription = "Country flag",
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = CurrencyKey.valueOf(
                            data.code
                        ).name,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.White
                    )
                }
            )
        }
    }
}

@Composable
fun CurrencyInputs(
    source: RequestState<CurrencyObject>,
    target: RequestState<CurrencyObject>,
    onChooseCurrencyKind: (CurrencyKind) -> Unit,
    onSwitchClick: () -> Unit
){

    var animationInitialized by remember { mutableStateOf(false) }

    val rotationAnimation by animateFloatAsState (
        targetValue = if (animationInitialized) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ViewCurrency(
            titleHolder = "From currency",
            currencyName = source,
            onClick = {if (source.isSuccess()) {
                onChooseCurrencyKind(
                    CurrencyKind.FromSource(currencyCode = CurrencyKey.valueOf(
                        source.getSuccessData().code
                    ))
                )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .graphicsLayer { rotationY = rotationAnimation },
            onClick = {
                animationInitialized = !animationInitialized
                onSwitchClick()
            }
        ){
            Icon(
                modifier = Modifier.size(80.dp).fillMaxSize(),
                painter = painterResource(Res.drawable.money_exchange),
                contentDescription = "Switch currency icon",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ViewCurrency(
            titleHolder = "to",
            currencyName = target,
            onClick = {
                if (target.isSuccess()) {
                    onChooseCurrencyKind(
                        CurrencyKind.ToTarget(currencyCode = CurrencyKey.valueOf(
                            target.getSuccessData().code
                        ))
                    )
            }
            }
        )
    }
}

@Composable
fun InputAmountNumber(
    amountNumber: Double,
    onAmountNumberChange: (Double) -> Unit
){

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 9.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amountNumber",
        onValueChange = { onAmountNumberChange(it.toDouble()) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.05f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
            disabledContainerColor = Color.White.copy(alpha = 0.05f),
            errorContainerColor = Color.White.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )

    )
}
