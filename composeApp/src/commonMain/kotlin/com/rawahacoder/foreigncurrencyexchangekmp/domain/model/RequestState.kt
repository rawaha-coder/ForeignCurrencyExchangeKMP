package com.rawahacoder.foreigncurrencyexchangekmp.domain.model

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import io.realm.kotlin.internal.platform.threadId

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading: RequestState<Nothing>()
    data class Success<out T>(val data: T): RequestState<T>()
    data class Error(val message: String): RequestState<Nothing>()


    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun getSuccessData() = (this as Success).data
    fun getErrorMessage() = (this as Error).message
}

@Composable
fun <T> RequestState<T>.ShowResult(
    specificationTransitionAnimation: ContentTransform =
        scaleIn(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500))
    togetherWith
        scaleOut(tween(durationMillis = 500)) + fadeOut(tween(durationMillis = 500)),
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: (@Composable () -> Unit)? = null,
    onError: (@Composable (String) -> Unit)? = null,
    onSuccess: @Composable (T) -> Unit
){
    AnimatedContent(
        targetState = this,
        transitionSpec = {specificationTransitionAnimation},
        label = "Animation"
    ){
        state ->
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            when(state){
                is RequestState.Idle -> {onIdle?.invoke()}
                is RequestState.Loading -> {onLoading?.invoke()}
                is RequestState.Error -> {onError?.invoke(state.getErrorMessage())}
                is RequestState.Success -> {onSuccess.invoke(state.getSuccessData())}
            }
        }
    }

}