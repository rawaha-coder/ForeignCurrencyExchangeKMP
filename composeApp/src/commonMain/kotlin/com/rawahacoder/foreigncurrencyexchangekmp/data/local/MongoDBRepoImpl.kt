package com.rawahacoder.foreigncurrencyexchangekmp.data.local

import com.rawahacoder.foreigncurrencyexchangekmp.domain.MongoDBRepo
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.CurrencyObject
import com.rawahacoder.foreigncurrencyexchangekmp.domain.model.RequestState
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MongoDBRepoImpl: MongoDBRepo {

    private var realmVar: Realm? = null

    init {
        settingRealm()
    }

    override fun settingRealm() {
        if (realmVar == null || realmVar!!.isClosed()){
            val configRealm = RealmConfiguration.Builder(
                schema = setOf(CurrencyObject::class)
            ).compactOnLaunch().build()

            realmVar =  Realm.open(configRealm)
        }
    }

    override suspend fun addCurrencyAmountData(currencyObject: CurrencyObject) {
        realmVar?.write {
            copyToRealm(currencyObject)
        }
    }

    override fun retrieveCurrencyAmountData(): Flow<RequestState<List<CurrencyObject>>> {
        return realmVar?.query<CurrencyObject>()?.asFlow()?.map { result ->
            RequestState.Success(data = result.list)
        } ?: flow {
            RequestState.Error(message = "Realm setting not successful")
        }
    }

    override suspend fun dbCleanUp() {
        realmVar?.write {
            val oldDataFromDB = this.query<CurrencyObject>()
            delete(oldDataFromDB)
        }
    }
}