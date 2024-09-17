package com.rawahacoder.foreigncurrencyexchangekmp.data.local

import com.rawahacoder.foreigncurrencyexchangekmp.domain.SharedPreferencesRepo
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SharedPreferencesImpl(private val settings: Settings) : SharedPreferencesRepo{
    companion object{
        const val KEY_TIME_STAMP = "whenLastUpdated"
    }

    @OptIn(ExperimentalSettingsApi::class)
    private val settingsProceed: FlowSettings = (settings as ObservableSettings).toFlowSettings()

    @OptIn(ExperimentalSettingsApi::class)
    override suspend fun storedWhenLastUpdated(lastUpdate: String) {
        settingsProceed.putLong(
            key = KEY_TIME_STAMP,
            value = Instant.parse(lastUpdate).toEpochMilliseconds()
        )
    }

    @OptIn(ExperimentalSettingsApi::class)
    override suspend fun isFetchedDataFresh(currentTimeStamp: Long): Boolean {
        val storedTimeStamp = settingsProceed.getLong(
            key = KEY_TIME_STAMP,
            defaultValue = 0L
        )
        return if (storedTimeStamp != 0L){
            val current = Instant.fromEpochMilliseconds(currentTimeStamp)
            val saved = Instant.fromEpochMilliseconds(storedTimeStamp)

            val currentDataTime = current.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = saved.toLocalDateTime(TimeZone.currentSystemDefault())

            val differenceInDays = currentDataTime.date.dayOfYear - savedDateTime.date.dayOfYear

            differenceInDays < 1

        }else false
    }

}