package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RateCondition
import com.rawahacoder.foreigncurrencyexchangekmp.getPlatform
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.headerColor
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.staleColor
import com.rawahacoder.foreigncurrencyexchangekmp.utility.showCurrentDateTime
import foreigncurrencyexchangekmp.composeapp.generated.resources.Res
import foreigncurrencyexchangekmp.composeapp.generated.resources.money_exchange

import org.jetbrains.compose.resources.painterResource


@Composable
fun HomePageHeader(status: RateCondition, onRatesRefresh: () -> Unit){
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

    }

}

@Composable
fun RatesCondition(status: RateCondition, onRatesRefresh: () -> Unit) {

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
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.money_exchange),
                    contentDescription = "Refresh icon",
                    tint = staleColor
                )
            }
        }

    }

}
