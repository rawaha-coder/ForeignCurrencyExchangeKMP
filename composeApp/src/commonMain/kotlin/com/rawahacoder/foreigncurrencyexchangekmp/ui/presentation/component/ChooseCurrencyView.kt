package com.rawahacoder.foreigncurrencyexchangekmp.ui.presentation.component


import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyKey
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.primaryColor
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.surfaceColor
import com.rawahacoder.foreigncurrencyexchangekmp.ui.theme.textColor
import org.jetbrains.compose.resources.painterResource


@Composable
fun CurrencyPickerView(
    keyCurrency: CurrencyKey,
    isSelected: Boolean,
    onSelect: (CurrencyKey) -> Unit
){
    val saturationValue = remember { Animatable(if (isSelected) 1f else 0f) }

    LaunchedEffect(isSelected){
        saturationValue.animateTo(if (isSelected) 1f else 0f)
    }

    val matrixColorVal = remember(saturationValue.value) {
        ColorMatrix().apply {
            setToSaturation(saturationValue.value)
        }
    }

    val alphaAnimatedVal by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.5f,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 9.dp))
            .clickable { onSelect(keyCurrency) }
            .padding(all = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(keyCurrency.flagCountry),
                contentDescription = "Currency country flag",
                colorFilter = ColorFilter.colorMatrix(matrixColorVal)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.alpha(alphaAnimatedVal),
                text = keyCurrency.name,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }

        CurrencyKeySelector(isSelected)
    }

}

@Composable
fun CurrencyKeySelector(isSelected: Boolean = false) {
    val colorAnimated by animateColorAsState(
        targetValue = if (isSelected) primaryColor else textColor.copy(alpha = 0.1f),
        animationSpec = tween(durationMillis = 300)
    )
    Box(
        modifier = Modifier
            .size(19.dp)
            .clip(CircleShape)
            .background(colorAnimated),
        contentAlignment = Alignment.Center
    ){
        if (isSelected){
            Icon(
                modifier = Modifier.size(13.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "Check icon indicating user selection",
                tint = surfaceColor
            )
        }
    }
}
