package com.example.explorr.data.remote

import com.squareup.moshi.Json


data class CountrysNameObject(
    @field:Json(name = "common")
    var commonName: String

)

data class LanguageObject(
    @field:Json(name = "ara")
    var language: String
)

data class FlagsObject(
    @field:Json(name = "png")
    var picture:String
)
data class CurrencyObject(
    @field:Json(name ="AWG")
    var AWG: AwgObject
)
data class IddObject(
    val root:String,
    var suffixes: List<String>
)

data class AwgObject(
    var name: String,
    var symbol: String
)
data class CarObject(
    var signs: List<String>,
    var side : String
)
data class CoatOfArmsObject(
    @field:Json(name = "png")
    var picture: String
)