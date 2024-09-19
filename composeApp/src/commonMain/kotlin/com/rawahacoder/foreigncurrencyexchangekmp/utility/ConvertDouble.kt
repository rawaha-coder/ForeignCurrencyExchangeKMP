package com.rawahacoder.foreigncurrencyexchangekmp.utility

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter

class ConvertDouble: TwoWayConverter<Double, AnimationVector1D>{
    override val convertFromVector: (AnimationVector1D) -> Double ={
            vector -> vector.value.toDouble()
    }
    override val convertToVector: (Double) -> AnimationVector1D ={
            vector -> AnimationVector1D(vector.toFloat())
    }
}