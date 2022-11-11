package com.example.explorr.data.remote

import com.squareup.moshi.Json
import java.text.DateFormatSymbols

data class CountriesDto(
    @field:Json(name = "name")
    val countryName: CountrysNameObject,
    var independent: Boolean,
    @field:Json(name = "capital")
    var capital: List<String>,
    var region: String,
    var languages: LanguageObject,
    var area:Float,
    var population:Int,
    var timezones: List<String>,
    @field:Json(name = "flags")
    var flags: FlagsObject,
    @field:Json(name = "coatOfArms")
    var coatOfArmsImg: CoatOfArmsObject,
    var currencies: CurrencyObject,
    var dialCode: IddObject,
    var drivingSide: CarObject


    )


