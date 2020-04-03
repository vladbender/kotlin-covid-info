package covidInfo

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class CovidInfo(
    val cases: Long,
    val deaths: Long,
    val recovered: Long,
    val updated: Long,
    val active: Int,
    val affectedCountries: Int
)

@Serializable
data class Timeline(
    val cases: Map<String, Int>,
    val deaths: Map<String, Int>,
    val recovered: Map<String, Int>
)

@Serializable
data class CountryInfo(
    val country: String,
    val province: String?,
    val timeline: Timeline
)

private val json = Json(JsonConfiguration.Stable)

fun parseAllCountryInfo(rawInfo: String): List<CountryInfo> = json.parse(CountryInfo.serializer().list, rawInfo)

fun parseTotalCovidInfo(rawInfo: String): CovidInfo = json.parse(CovidInfo.serializer(), rawInfo)
