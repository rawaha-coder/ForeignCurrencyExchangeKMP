package com.rawahacoder.foreigncurrencyexchangekmp.domain.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class ApiResponse(
    val meta: MedaDataFromApi,
    val data: Map<String, CurrencyObject>
)

@Serializable
data class MedaDataFromApi(
    val last_updated_at: String
)

@Serializable
open class CurrencyObject: RealmObject{

    companion object

    @PrimaryKey
    var docID: ObjectId = ObjectId()
    var code: String = ""
    var value: Double = 0.0

}