package com.rawahacoder.foreigncurrencyexchangekmp.data.remote.thirdpartyapi

import com.rawahacoder.foreigncurrencyexchangekmp.domain.ForeignCurrencyExchangeApiService
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.ApiResponse
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ForeignCurrencyExchangeApiServiceImpl: ForeignCurrencyExchangeApiService {

    private val httpClient = HttpClient{
        install(ContentNegotiation){
            json(
                Json{
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }

            )
        }
        install(HttpTimeout){
            requestTimeoutMillis = 16000
        }
        install(DefaultRequest){
            headers{
                append("apikey", API_KEY_CURRENCY_EXCHANGE)
            }
        }
    }

    override suspend fun retrieveLatestForeignCurrencyExchangeRate(): RequestState<List<CurrencyObject>> {

        return try {
            val responseFromApi = httpClient.get(END_POINT_API)

            if (responseFromApi.status.value == 200){

                println("Response from Api =" + responseFromApi.body<String>())

                val responseDataFromApi = Json.decodeFromString<ApiResponse>(responseFromApi.body())
                RequestState.Success(data = responseDataFromApi.data.values.toList())
            }else{
                RequestState.Error("HTTP Error: ${responseFromApi.status}")
            }
        }
        catch (error: Exception){
            RequestState.Error(error.message.toString())
        }
    }

}

