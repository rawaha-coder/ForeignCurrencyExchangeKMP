package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyKey
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyKind
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.primaryColor
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.textColor

@Composable
fun ChooseCurrencyDialog(
    currenciesList: List<CurrencyObject>,
    currencyKind: CurrencyKind,
    onConfirmClick: (CurrencyKey) -> Unit,
    onDismiss: () -> Unit
){
    val overallCurrencies = remember(key1 = currenciesList){
        mutableStateListOf<CurrencyObject>().apply { addAll(currenciesList) }
    }

    var searchCurrencyQuery by remember { mutableStateOf("") }

    var chosenCurrencyKey by remember(currencyKind) {
        mutableStateOf(currencyKind.key)
    }

    AlertDialog(
        containerColor = Color.White,
        title = { Text(
            text = "Search and choose currency",
            color = textColor
        )},
        text = {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(size = 100.dp)),
                    value = searchCurrencyQuery,
                    onValueChange = {
                        userInput ->
                        searchCurrencyQuery = userInput.uppercase()

                        if (userInput.isNotEmpty()) {
                            val filteredCurrencies = overallCurrencies.filter {
                                it.code.contains(userInput.uppercase())
                            }

                            overallCurrencies.clear()
                            overallCurrencies.addAll(filteredCurrencies)
                        }else{
                            overallCurrencies.clear()
                            overallCurrencies.addAll(currenciesList)
                        }
                    },
                    placeholder = {

                        Text(
                            text = "Write here...",
                            color = textColor.copy(alpha = 0.4f),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = textColor.copy(alpha = 0.1f),
                        unfocusedContainerColor = textColor.copy(alpha = 0.1f),
                        disabledContainerColor = textColor.copy(alpha = 0.1f),
                        errorContainerColor = textColor.copy(alpha = 0.1f),
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColor
                    )
                )

                Spacer(modifier = Modifier.height(22.dp))

                AnimatedContent(
                    targetState = overallCurrencies
                    ){
                    currenciesAvailable ->
                    if (currenciesAvailable.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            verticalArrangement = Arrangement.spacedBy(9.dp)
                        ) {
                            items(
                                items = currenciesAvailable,
                                key = {it.docID.toHexString()}
                            ){
                                currencyOption ->
                                CurrencyPickerView(
                                    keyCurrency = CurrencyKey.valueOf(currencyOption.code),
                                    isSelected = chosenCurrencyKey.name == currencyOption.code,
                                    onSelect = {chosenCurrencyKey = it}
                                )
                            }
                        }
                    }
                    else{
                        ErrorScreen(modifier = Modifier.height(250.dp))
                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss){
                Text(
                    text = "Close",
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmClick(chosenCurrencyKey) }){
                Text(
                    text = "Confirm",
                    color = primaryColor
                )
            }
        }
    )

}