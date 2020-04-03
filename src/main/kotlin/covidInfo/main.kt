package covidInfo

import java.util.*

fun main() {
    printTotalInfo()
    saveHistorical()
}

private fun saveHistorical() {
    val rawInfo = request("https://corona.lmao.ninja/v2/historical")

    val allCountriesInfo = parseAllCountryInfo(rawInfo)

    val graphsFolderPath = "graphs"

    createFolderIfNotExists(graphsFolderPath)

    for (country in allCountriesInfo) {
        val filename = createFilename(country.country, country.province)

        println("Generate $filename")

        val cases = generateGraph("$filename all cases", convertFromMapToListOfTimePoints(country.timeline.cases))
        val deaths = generateGraph("$filename deaths", convertFromMapToListOfTimePoints(country.timeline.deaths))
        val recovered = generateGraph("$filename recovered", convertFromMapToListOfTimePoints(country.timeline.recovered))

        saveToDisk("$graphsFolderPath/$filename.txt", cases + deaths + recovered)
    }
}

private fun printTotalInfo() {
    val rawInfo = request("https://corona.lmao.ninja/all")

    val covidInfo = parseTotalCovidInfo(rawInfo)

    println("Total cases: ${covidInfo.cases}")
    println("Death: ${covidInfo.deaths}")
    println("Recovered: ${covidInfo.recovered}")
    println("Updated: ${Date(covidInfo.updated)}")
    println("Active: ${covidInfo.active}")
    println("Affected countries: ${covidInfo.affectedCountries}")
    println()
}

private fun createFilename(country: String, province: String?): String =
    clearName(country)
        .plus(if (province != null) " ${clearName(province)}" else "")

private fun clearName(name: String) = name.replace(Regex("[^\\w ]"), "")
